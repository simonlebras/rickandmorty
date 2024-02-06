package app.rickandmorty.feature.settings.util

import androidx.annotation.StringRes
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.feature.settings.R

@get:StringRes
internal val NightMode.label: Int
    get() = when (this) {
        NightMode.AutoBattery -> R.string.settings_theme_auto_battery
        NightMode.FollowSystem -> R.string.settings_theme_follow_system
        NightMode.Light -> R.string.settings_theme_light
        NightMode.Dark -> R.string.settings_theme_dark
    }
