package app.rickandmorty.feature.settings.util

import androidx.annotation.StringRes
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.feature.settings.R

@get:StringRes
internal val NightMode.label: Int
    get() = when (this) {
        NightMode.AUTO_BATTERY -> R.string.settings_theme_auto_battery
        NightMode.FOLLOW_SYSTEM -> R.string.settings_theme_follow_system
        NightMode.LIGHT -> R.string.settings_theme_light
        NightMode.DARK -> R.string.settings_theme_dark
    }