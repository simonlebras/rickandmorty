package app.rickandmorty.ui.settings.common.util

import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_theme_auto_battery
import app.rickandmorty.core.l10n.resources.settings_theme_dark
import app.rickandmorty.core.l10n.resources.settings_theme_follow_system
import app.rickandmorty.core.l10n.resources.settings_theme_light
import app.rickandmorty.data.model.NightMode
import org.jetbrains.compose.resources.StringResource

public val NightMode.label: StringResource
  get() =
    when (this) {
      NightMode.AutoBattery -> L10nRes.string.settings_theme_auto_battery
      NightMode.FollowSystem -> L10nRes.string.settings_theme_follow_system
      NightMode.Light -> L10nRes.string.settings_theme_light
      NightMode.Dark -> L10nRes.string.settings_theme_dark
    }
