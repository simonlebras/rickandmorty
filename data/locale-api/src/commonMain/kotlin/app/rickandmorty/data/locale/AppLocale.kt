package app.rickandmorty.data.locale

import kotlinx.serialization.Serializable

@Serializable
public sealed interface AppLocale {
  @Serializable public data object System : AppLocale

  @Serializable
  public data class Specific(val languageTag: String) : AppLocale {
    public val displayName: String
      get() = platformLocaleDelegate.fromLanguageTag(languageTag).getLocalizedName()
  }
}
