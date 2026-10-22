package app.rickandmorty.data.locale

public sealed interface AppLocale {
  public data object System : AppLocale

  public data class Specific(val languageTag: String) : AppLocale {
    public val displayName: String
      get() = platformLocaleDelegate.fromLanguageTag(languageTag).getLocalizedName()
  }
}
