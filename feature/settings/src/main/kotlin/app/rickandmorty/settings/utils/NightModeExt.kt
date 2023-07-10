package app.rickandmorty.settings.utils

import androidx.annotation.StringRes
import app.rickandmorty.settings.R
import app.rickandmorty.theme.domain.NightMode

@get:StringRes
internal val NightMode.label: Int
    get() = when (this) {
        NightMode.AUTO_BATTERY -> R.string.settings_appearance_auto_battery
        NightMode.FOLLOW_SYSTEM -> R.string.settings_appearance_follow_system
        NightMode.LIGHT -> R.string.settings_appearance_light
        NightMode.DARK -> R.string.settings_appearance_dark
    }
