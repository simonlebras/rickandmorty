package app.rickandmorty.feature.settings.util

import androidx.annotation.StringRes
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.data.model.NightMode

@get:StringRes
internal val NightMode.label: Int
    get() = when (this) {
        NightMode.AutoBattery -> L10nR.string.settings_theme_auto_battery
        NightMode.FollowSystem -> L10nR.string.settings_theme_follow_system
        NightMode.Light -> L10nR.string.settings_theme_light
        NightMode.Dark -> L10nR.string.settings_theme_dark
    }
