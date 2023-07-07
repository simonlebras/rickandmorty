package app.rickandmorty.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.settings.language.LanguageSettingsScreen
import app.rickandmorty.settings.main.MainSettingsScreen

private const val mainSettingsRoute = "mainSettings"
private const val languageSettingsRoute = "languageSettings"

public fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = mainSettingsRoute,
        builder = builder,
    )
}

private fun NavHostController.navigateToLanguageSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = languageSettingsRoute,
        builder = builder,
    )
}

public fun NavGraphBuilder.settings(
    navController: NavHostController,
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
