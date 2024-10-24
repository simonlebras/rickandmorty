package app.rickandmorty.data.model

import java.util.Locale as JavaLocale

public actual typealias PlatformLocale = JavaLocale

internal actual fun PlatformLocale.getLocalizedName(): String = getDisplayName(this)

internal actual fun PlatformLocale.getLanguageTag(): String = toLanguageTag()

internal actual fun createPlatformLocaleDelegate(): PlatformLocaleDelegate = PlatformLocaleDelegate { languageTag ->
    JavaLocale.forLanguageTag(languageTag)
}
