package app.rickandmorty.settings.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import app.rickandmorty.settings.R
import app.rickandmorty.theme.domain.NightMode

@Composable
internal fun NightMode.toText(): String {
    val text = when (this) {
        NightMode.AUTO_BATTERY -> R.string.settings_dark_mode_auto_battery
        NightMode.FOLLOW_SYSTEM -> R.string.settings_dark_mode_follow_system
        NightMode.LIGHT -> R.string.settings_dark_mode_light
        NightMode.DARK -> R.string.settings_dark_mode_dark
    }
    return stringResource(text)
}
