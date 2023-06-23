package app.rickandmorty.settings

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

public const val settingsRoute: String = "settings"

public fun NavController.navigateToSettings(navOptions: NavOptions? = null) {
    navigate(settingsRoute, navOptions)
}

public fun NavGraphBuilder.settings(
    onNavigateUp: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
) {
    composable(route = settingsRoute) {
        SettingsScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }
}
