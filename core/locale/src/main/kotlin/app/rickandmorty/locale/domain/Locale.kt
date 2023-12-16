package app.rickandmorty.locale.domain

import java.util.Locale as JavaLocale

public data class Locale internal constructor(private val javaLocale: JavaLocale) {
    public constructor(languageTag: String) : this(JavaLocale.forLanguageTag(languageTag))

    public fun getDisplayName(locale: Locale): String = javaLocale.getDisplayName(locale.javaLocale)

    public fun toLanguageTag(): String = javaLocale.toLanguageTag()
}
