package app.rickandmorty.feature.settings.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.settings.language.LanguageSettingsScreen
import app.rickandmorty.feature.settings.main.MainSettingsScreen
import app.rickandmorty.feature.settings.theme.ThemeSettingsDialog
import kotlinx.serialization.Serializable

@Serializable
private data object MainSettings

@Serializable
private data object LanguageSettings

public fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = MainSettings,
        builder = builder,
    )
}

private fun NavHostController.navigateToLanguageSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = LanguageSettings,
        builder = builder,
    )
}

public fun NavGraphBuilder.settings(
    navController: NavHostController,
    onNavigateUp: () -> Unit,
    onNavigateToOssLicenses: () -> Unit,
) {
    composable<MainSettings> {
        var showThemeSettingsDialog by rememberSaveable {
            mutableStateOf(false)
        }

        if (showThemeSettingsDialog) {
            ThemeSettingsDialog(
                onDismiss = { showThemeSettingsDialog = false },
            )
        }

        MainSettingsScreen(
            onNavigateUp = onNavigateUp,
            onNavigateToThemeSettings = { showThemeSettingsDialog = true },
            onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
            onNavigateToOssLicenses = onNavigateToOssLicenses,
        )
    }

    composable<LanguageSettings> {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
