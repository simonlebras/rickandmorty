package app.rickandmorty.data.model

import platform.Foundation.NSLocale
import platform.Foundation.localeIdentifier
import platform.Foundation.localizedStringForLanguageCode

public actual typealias PlatformLocale = NSLocale

internal actual fun PlatformLocale.getLocalizedName(): String = localizedStringForLanguageCode(localeIdentifier)!!

internal actual fun PlatformLocale.getLanguageTag(): String = localeIdentifier

internal actual fun createPlatformLocaleDelegate(): PlatformLocaleDelegate = PlatformLocaleDelegate { languageTag ->
    NSLocale(languageTag)
}
