package app.rickandmorty.data.locale

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.RECEIVER_NOT_EXPORTED
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

@ContributesBinding(AppScope::class)
public class AndroidPlatformLocaleManager(
  @AppContext private val context: Context,
  private val dataStore: DataStore<LocaleProto>,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : PlatformLocaleManager {
  override fun getAppLocale(): Flow<Locale?> {
    val localeFlow =
      if (Build.VERSION.SDK_INT >= 33) {
        callbackFlow {
          val localeManager = context.getSystemService<LocaleManager>()!!

          trySend(localeManager.appLocale)

          val receiver =
            object : BroadcastReceiver() {
              override fun onReceive(context: Context, intent: Intent) {
                trySend(localeManager.appLocale)
              }
            }
          ContextCompat.registerReceiver(
            context,
            receiver,
            IntentFilter(Intent.ACTION_LOCALE_CHANGED),
            RECEIVER_NOT_EXPORTED,
          )

          awaitClose { context.unregisterReceiver(receiver) }
        }
      } else {
        dataStore.data.map { locale ->
          locale.appLocale?.let { locale -> Locale(languageTag = locale) }
        }
      }

    return localeFlow.distinctUntilChanged()
  }

  @SuppressLint("DiscouragedApi")
  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> {
    return withContext(ioDispatcher) {
      val locales = mutableListOf<Locale>()
      val resources = context.resources
      val localeConfigFileId =
        resources.getIdentifier(LOCALE_CONFIG_FILE, "xml", context.packageName)
      resources.getXml(localeConfigFileId).use { parser ->
        while (parser.eventType != XmlPullParser.END_DOCUMENT) {
          if (parser.eventType == XmlPullParser.START_TAG && parser.name == "locale") {
            locales += Locale(parser.getAttributeValue(0))
          }
          parser.next()
        }
      }
      locales.toImmutableList()
    }
  }
}
