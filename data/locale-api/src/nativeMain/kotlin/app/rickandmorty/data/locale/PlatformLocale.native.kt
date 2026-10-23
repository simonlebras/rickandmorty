package app.rickandmorty.data.locale

import platform.Foundation.NSLocale
import platform.Foundation.localeIdentifier
import platform.Foundation.localizedStringForLanguageCode

internal actual typealias PlatformLocale = NSLocale

internal actual fun PlatformLocale.getLocalizedName(): String =
  localizedStringForLanguageCode(localeIdentifier)!!

internal actual fun createPlatformLocaleDelegate(): PlatformLocaleDelegate =
  PlatformLocaleDelegate { languageTag ->
    NSLocale(languageTag)
  }
