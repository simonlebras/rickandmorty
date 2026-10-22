package app.rickandmorty.data.locale

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import java.util.Locale as JavaLocale
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

@ContributesBinding(AppScope::class)
internal class AndroidPlatformLocaleManager(
  @AppContext private val context: Context,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : PlatformLocaleManager {
  override fun getAppLocale(): Flow<AppLocale> {
    return callbackFlow {
      trySend(AppCompatDelegate.getApplicationLocales().toAppLocale())

      val receiver =
        object : BroadcastReceiver() {
          override fun onReceive(context: Context, intent: Intent) {
            trySend(AppCompatDelegate.getApplicationLocales().toAppLocale())
          }
        }
      context.registerReceiver(
        receiver,
        IntentFilter(Intent.ACTION_LOCALE_CHANGED),
        RECEIVER_NOT_EXPORTED,
      )
      awaitClose { context.unregisterReceiver(receiver) }
    }
      .distinctUntilChanged()
  }

  override suspend fun setAppLocale(appLocale: AppLocale) {
    val localeList =
      when (appLocale) {
        AppLocale.System -> LocaleListCompat.getEmptyLocaleList()
        is AppLocale.Specific -> LocaleListCompat.forLanguageTags(appLocale.languageTag)
      }
    AppCompatDelegate.setApplicationLocales(localeList)
  }

  @SuppressLint("DiscouragedApi")
  override suspend fun getAvailableAppLocales(): ImmutableList<AppLocale.Specific> {
    return withContext(ioDispatcher) {
      val resources = context.resources
      val localeConfigFileId =
        resources.getIdentifier(LOCALE_CONFIG_FILE, "xml", context.packageName)
      resources.getXml(localeConfigFileId).use { parser ->
        buildList {
          while (parser.eventType != XmlPullParser.END_DOCUMENT) {
            if (parser.eventType == XmlPullParser.START_TAG && parser.name == "locale") {
              add(AppLocale.Specific(normalizeLanguageTag(parser.getAttributeValue(0))))
            }
            parser.next()
          }
        }
          .toImmutableList()
      }
    }
  }
}

private fun LocaleListCompat.toAppLocale(): AppLocale {
  return get(0)?.let { AppLocale.Specific(it.toLanguageTag()) } ?: AppLocale.System
}

private fun normalizeLanguageTag(languageTag: String): String =
  JavaLocale.forLanguageTag(languageTag).toLanguageTag()
