package app.rickandmorty.data.locale

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

@ContributesBinding(AppScope::class)
@SingleIn(AppScope::class)
public class AndroidPlatformLocaleManager(
  @AppContext private val context: Context,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : PlatformLocaleManager {
  private val mutex = Mutex()
  private val compatLocaleUpdates = MutableSharedFlow<Locale?>(replay = 1)

  override fun getAppLocale(): Flow<Locale?> {
    val initialLocale = flow { emit(AppCompatDelegate.getApplicationLocales().toLocale()) }

    val localeUpdates =
      if (Build.VERSION.SDK_INT >= 33) {
        callbackFlow {
          val receiver =
            object : BroadcastReceiver() {
              override fun onReceive(context: Context, intent: Intent) {
                trySend(AppCompatDelegate.getApplicationLocales().toLocale())
              }
            }
          context.registerReceiver(
            receiver,
            IntentFilter(Intent.ACTION_LOCALE_CHANGED),
            RECEIVER_NOT_EXPORTED,
          )
          awaitClose { context.unregisterReceiver(receiver) }
        }
      } else {
        compatLocaleUpdates
      }

    return merge(initialLocale, localeUpdates).distinctUntilChanged()
  }

  override suspend fun setAppLocale(locale: Locale?) {
    mutex.withLock {
      val localeList =
        locale?.let { LocaleListCompat.forLanguageTags(it.toLanguageTag()) }
          ?: LocaleListCompat.getEmptyLocaleList()
      AppCompatDelegate.setApplicationLocales(localeList)

      if (Build.VERSION.SDK_INT < 33) {
        compatLocaleUpdates.emit(locale)
      }
    }
  }

  @SuppressLint("DiscouragedApi")
  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> {
    return withContext(ioDispatcher) {
      val resources = context.resources
      val localeConfigFileId =
        resources.getIdentifier(LOCALE_CONFIG_FILE, "xml", context.packageName)
      resources.getXml(localeConfigFileId).use { parser ->
        buildList {
            while (parser.eventType != XmlPullParser.END_DOCUMENT) {
              if (parser.eventType == XmlPullParser.START_TAG && parser.name == "locale") {
                add(Locale(parser.getAttributeValue(0)))
              }
              parser.next()
            }
          }
          .toImmutableList()
      }
    }
  }
}

private fun LocaleListCompat.toLocale(): Locale? {
  return get(0)?.let { Locale(it.toLanguageTag()) }
}
