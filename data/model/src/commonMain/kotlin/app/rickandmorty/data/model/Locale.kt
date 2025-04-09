package app.rickandmorty.data.model

public data class Locale internal constructor(val platformLocale: PlatformLocale) {
  public constructor(
    languageTag: String
  ) : this(platformLocaleDelegate.fromLanguageTag(languageTag))

  public fun getLocalizedName(): String = platformLocale.getLocalizedName()

  public fun toLanguageTag(): String = platformLocale.getLanguageTag()
}
