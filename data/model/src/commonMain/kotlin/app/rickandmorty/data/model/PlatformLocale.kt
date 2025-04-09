package app.rickandmorty.data.model

public expect class PlatformLocale

internal expect fun PlatformLocale.getLocalizedName(): String

internal expect fun PlatformLocale.getLanguageTag(): String

internal fun interface PlatformLocaleDelegate {
  fun fromLanguageTag(languageTag: String): PlatformLocale
}

internal expect fun createPlatformLocaleDelegate(): PlatformLocaleDelegate

internal val platformLocaleDelegate = createPlatformLocaleDelegate()
