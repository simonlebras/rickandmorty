package app.rickandmorty.locale.data

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.LocaleManagerCompat
import androidx.core.os.LocaleListCompat
import app.rickandmorty.core.AppDelegate
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.locale.domain.LocaleRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.xmlpull.v1.XmlPullParser

private const val LOCALE_CONFIG_FILE = "_generated_res_locale_config"

internal class LocaleRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val appDelegate: AppDelegate,
) : LocaleRepository {
    override fun getApplicationLocale(): Flow<Locale?> {
        return appDelegate.configuration
            .map {
                LocaleManagerCompat.getApplicationLocales(context).get(0)?.let { locale ->
                    Locale(languageTag = locale.toLanguageTag())
                }
            }
            .distinctUntilChanged()
    }

    override fun setApplicationLocale(locale: Locale?) {
        val locales = if (locale == null) {
            LocaleListCompat.getEmptyLocaleList()
        } else {
            LocaleListCompat.forLanguageTags(locale.toLanguageTag())
        }
        AppCompatDelegate.setApplicationLocales(locales)
    }

    @SuppressLint("DiscouragedApi")
    override suspend fun getAvailableApplicationLocales(): ImmutableList<Locale> {
        val locales = mutableListOf<Locale>()
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
        return locales.toImmutableList()
    }
}
