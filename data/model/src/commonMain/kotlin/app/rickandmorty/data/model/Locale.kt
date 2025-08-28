package app.rickandmorty.data.model

public class Locale(languageTag: String) {
  public val platformLocale: PlatformLocale = platformLocaleDelegate.fromLanguageTag(languageTag)

  public fun getLocalizedName(): String = platformLocale.getLocalizedName()

  public fun toLanguageTag(): String = platformLocale.getLanguageTag()
}
