package app.rickandmorty.feature.settings.utils

import androidx.annotation.StringRes
import app.rickandmorty.feature.settings.R
import app.rickandmorty.theme.domain.NightMode

@get:StringRes
internal val NightMode.label: Int
    get() = when (this) {
        NightMode.AUTO_BATTERY -> R.string.settings_theme_auto_battery
        NightMode.FOLLOW_SYSTEM -> R.string.settings_theme_follow_system
        NightMode.LIGHT -> R.string.settings_theme_light
        NightMode.DARK -> R.string.settings_theme_dark
    }
