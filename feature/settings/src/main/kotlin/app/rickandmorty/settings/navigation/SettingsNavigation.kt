package app.rickandmorty.settings.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import app.rickandmorty.settings.language.LanguageSettingsScreen
import app.rickandmorty.settings.main.MainSettingsScreen

private const val mainSettingsRoute = "mainSettings"
private const val languageSettingsRoute = "languageSettings"

public fun NavController.navigateToMainSettings(navOptions: NavOptions? = null) {
    navigate(mainSettingsRoute, navOptions)
}

private fun NavController.navigateToLanguageSettings(navOptions: NavOptions? = null) {
    navigate(languageSettingsRoute, navOptions)
}

public fun NavGraphBuilder.settings(
    navController: NavController,
    onNavigateUp: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
) {
    composable(route = mainSettingsRoute) {
        MainSettingsScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }

    composable(route = languageSettingsRoute) {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
