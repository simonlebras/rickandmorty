package app.rickandmorty.settings.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import app.rickandmorty.settings.language.LanguageSettingsScreen
import app.rickandmorty.settings.main.MainSettingsScreen
import app.rickandmorty.settings.theme.ThemeSettingsDialog

private const val mainSettingsRoute = "mainSettings"
private const val themeSettingsRoute = "themeSettings"
private const val languageSettingsRoute = "languageSettings"

public fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = mainSettingsRoute,
        builder = builder,
    )
}

private fun NavHostController.navigateToThemeSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = themeSettingsRoute,
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
            onNavigateToThemeSettings = navController::navigateToThemeSettings,
            onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }

    dialog(route = themeSettingsRoute) {
        ThemeSettingsDialog(
            onDismiss = navController::popBackStack,
        )
    }

    composable(route = languageSettingsRoute) {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
