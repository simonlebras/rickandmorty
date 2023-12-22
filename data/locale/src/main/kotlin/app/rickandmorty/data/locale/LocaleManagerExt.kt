package app.rickandmorty.data.locale

import android.app.LocaleManager
import androidx.annotation.RequiresApi
import app.rickandmorty.data.model.Locale

internal val LocaleManager.appLocale: Locale?
    @RequiresApi(33)
    get() = applicationLocales[0]?.let { locale ->
        Locale(languageTag = locale.toLanguageTag())
    }
