package app.rickandmorty.ui.settings.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable public data object MainSettingsNavKey : NavKey

@Serializable public data object LanguageSettingsNavKey : NavKey

@Serializable public data object LicenseSettingsNavKey : NavKey
