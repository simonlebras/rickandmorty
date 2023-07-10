package app.rickandmorty.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import app.rickandmorty.settings.appearance.AppearanceSettingsDialog
import app.rickandmorty.settings.language.LanguageSettingsScreen
import app.rickandmorty.settings.main.MainSettingsScreen

private const val mainSettingsRoute = "mainSettings"
private const val appearanceSettingsRoute = "appearanceSettings"
private const val languageSettingsRoute = "languageSettings"

public fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = mainSettingsRoute,
        builder = builder,
    )
}

private fun NavHostController.navigateToAppearanceSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = appearanceSettingsRoute,
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
            onNavigateToAppearanceSettings = navController::navigateToAppearanceSettings,
            onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }

    dialog(route = appearanceSettingsRoute) {
        AppearanceSettingsDialog(
            onDismiss = navController::popBackStack,
        )
    }

    composable(route = languageSettingsRoute) {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
