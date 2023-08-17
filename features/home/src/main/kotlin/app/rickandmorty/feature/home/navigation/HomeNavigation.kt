package app.rickandmorty.feature.home.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.layout.DisplayFeature
import app.rickandmorty.feature.home.HomeNavigationItem
import app.rickandmorty.feature.home.HomeNavigationScreen
import app.rickandmorty.feature.home.HomeScreen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneNavScope
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable as twopanelayoutComposable
import kotlinx.collections.immutable.ImmutableList

public const val homeRoute: String = "home"
public const val homeNavigationRoute: String = "homeNavigation"

public fun NavGraphBuilder.home(
    singlePaneStartDestination: String,
    pane1StartDestination: String,
    pane2StartDestination: String,
    navGraph: NavGraphBuilder.(NavHostController) -> Unit,
) {
    composable(route = homeRoute) {
        val navController = rememberNavController()

        HomeScreen(
            navController = navController,
            singlePaneStartDestination = singlePaneStartDestination,
            pane1StartDestination = pane1StartDestination,
            pane2StartDestination = pane2StartDestination,
            navGraph = { navGraph(navController) },
        )
    }
}

public fun NavGraphBuilder.homeNavigation(
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    navigationItems: ImmutableList<HomeNavigationItem>,
    navGraph: context(TwoPaneNavScope) NavGraphBuilder.() -> Unit,
) {
    twopanelayoutComposable(route = homeNavigationRoute) {
        val navController = rememberNavController()

        HomeNavigationScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            displayFeatures = displayFeatures,
            navigationItems = navigationItems,
            navGraph = { navGraph(this@twopanelayoutComposable, this) },
        )
    }
}
