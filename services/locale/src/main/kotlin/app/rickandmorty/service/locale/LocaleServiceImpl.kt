package app.rickandmorty.service.locale

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import app.rickandmorty.coroutines.IODispatcher
import app.rickandmorty.service.locale.model.Locale
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

@Singleton
internal class LocaleServiceImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher,
) : LocaleService {
    private val mutex = Mutex()

    private val applicationLocale = MutableStateFlow(
        LocaleManagerCompat.getApplicationLocales(context)[0]?.let { locale ->
            Locale(languageTag = locale.toLanguageTag())
        },
    )

    override fun getApplicationLocale(): Flow<Locale?> = applicationLocale

    override suspend fun setApplicationLocale(locale: Locale?) = mutex.withLock {
        val locales = if (locale == null) {
            LocaleListCompat.getEmptyLocaleList()
        } else {
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        }
        AppCompatDelegate.setApplicationLocales(locales)
        applicationLocale.update { locale }
    }

    override suspend fun getAvailableApplicationLocales(): ImmutableList<Locale> {
        return withContext(ioDispatcher) {
            val locales = mutableListOf<Locale>()

            @SuppressLint("DiscouragedApi")
            val localeConfigFileId = context.resources.getIdentifier(
                LOCALE_CONFIG_FILE,
                "xml",
                context.packageName,
            )
            context.resources.getXml(localeConfigFileId).use { parser ->
                while (parser.eventType != XmlPullParser.END_DOCUMENT) {
                    if (parser.eventType == XmlPullParser.START_TAG && parser.name == "locale") {
                        locales += (Locale(parser.getAttributeValue(0)))
                    }
                    parser.next()
                }
            }
            locales.toImmutableList()
        }
    }
}
