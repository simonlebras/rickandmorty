package app.rickandmorty.data.locale

import android.os.LocaleList
import androidx.core.os.LocaleListCompat
import app.rickandmorty.data.model.Locale

internal fun Locale?.toLocaleList(): LocaleList {
    return when (this) {
        null -> LocaleList.getEmptyLocaleList()
        else -> LocaleList.forLanguageTags(toLanguageTag())
    }
}

internal fun Locale?.toLocaleListCompat(): LocaleListCompat = LocaleListCompat.wrap(toLocaleList())
