package app.rickandmorty.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import app.rickandmorty.settings.dartheme.DarkThemeSettingsDialog
import app.rickandmorty.settings.language.LanguageSettingsScreen
import app.rickandmorty.settings.main.MainSettingsScreen

private const val mainSettingsRoute = "mainSettings"
private const val darkThemeSettingsRoute = "darkThemeSettings"
private const val languageSettingsRoute = "languageSettings"

public fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = mainSettingsRoute,
        builder = builder,
    )
}

private fun NavHostController.navigateToDarkThemeSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = darkThemeSettingsRoute,
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
            onNavigateToDarkThemeSettings = navController::navigateToDarkThemeSettings,
            onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }

    dialog(route = darkThemeSettingsRoute) {
        DarkThemeSettingsDialog(
            onDismiss = navController::popBackStack
        )
    }

    composable(route = languageSettingsRoute) {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
