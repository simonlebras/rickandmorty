package app.rickandmorty.ui.navigation

import android.os.Parcelable
import androidx.activity.compose.BackHandler
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
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
import app.rickandmorty.ui.settings.licenses.LicensesSettingsPane
import kotlinx.parcelize.Parcelize

private const val mainSettingsRoute = "mainSettings"
private const val languageSettingsRoute = "languageSettings"

fun NavHostController.navigateToMainSettings(builder: NavOptionsBuilder.() -> Unit = {}) {
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

@Parcelize
class MyItem(val id: Int) : Parcelable

@Parcelize
data object MyItem2 : Parcelable

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
public fun NavGraphBuilder.settings(
    navController: NavHostController,
    onNavigateUp: () -> Unit,
) {
    composable(route = mainSettingsRoute) {
        val navigator = rememberListDetailPaneScaffoldNavigator<MyItem>()

        BackHandler(navigator.canNavigateBack()) {
            navigator.navigateBack()
        }
        var showThemeSettingsDialog by rememberSaveable {
            mutableStateOf(false)
        }

        if (showThemeSettingsDialog) {
            ThemeSettingsDialog(
                onDismiss = { showThemeSettingsDialog = false },
            )
        }

        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    MainSettingsScreen(
                        onNavigateUp = onNavigateUp,
                        onNavigateToThemeSettings = { showThemeSettingsDialog = true },
                        onNavigateToLanguageSettings = navController::navigateToLanguageSettings,
                        onNavigateToOssLicenses = { navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, MyItem(2)) },
                    )
                }
            },
            detailPane = {
                AnimatedPane {
                    // Show the detail pane content if selected item is available
                    navigator.currentDestination?.content?.let {
                        LicensesSettingsPane(
                            onNavigateUp = {navigator.navigateBack()},
                        )
                    }
                }
            },
        )
    }

    composable(route = languageSettingsRoute) {
        LanguageSettingsScreen(
            onNavigateUp = navController::navigateUp,
        )
    }
}
