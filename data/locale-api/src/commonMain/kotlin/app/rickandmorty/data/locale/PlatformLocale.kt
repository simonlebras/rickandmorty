package app.rickandmorty.data.locale

internal expect class PlatformLocale

internal expect fun PlatformLocale.getLocalizedName(): String

internal fun interface PlatformLocaleDelegate {
  fun fromLanguageTag(languageTag: String): PlatformLocale
}

internal expect fun createPlatformLocaleDelegate(): PlatformLocaleDelegate

internal val platformLocaleDelegate = createPlatformLocaleDelegate()
