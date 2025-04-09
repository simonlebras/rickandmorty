package app.rickandmorty.data.locale

import android.annotation.SuppressLint
import android.app.LocaleManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.RECEIVER_NOT_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.core.content.getSystemService
import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.data.locale.proto.LocalePreferences
import app.rickandmorty.data.model.Locale
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

internal class LocaleRepositoryImpl(
  private val context: Context,
  private val dataStore: DataStore<LocalePreferences>,
  @ApplicationScope private val applicationScope: CoroutineScope,
  @IODispatcher val ioDispatcher: CoroutineDispatcher,
) : LocaleRepository {
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
          context.registerReceiver(
            receiver,
            IntentFilter(Intent.ACTION_LOCALE_CHANGED),
            RECEIVER_NOT_EXPORTED,
          )

          awaitClose { context.unregisterReceiver(receiver) }
        }
      } else {
        dataStore.data.map { preferences ->
          preferences.app_locale?.let { locale -> Locale(languageTag = locale) }
        }
      }

    return localeFlow.distinctUntilChanged()
  }

  override suspend fun setAppLocale(locale: Locale?) {
    applicationScope
      .launch {
        dataStore.updateData { preferences ->
          preferences.copy(app_locale = locale?.toLanguageTag())
        }
      }
      .join()
  }

  @SuppressLint("DiscouragedApi")
  override suspend fun getAvailableAppLocales(): ImmutableList<Locale> =
    withContext(ioDispatcher) {
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
