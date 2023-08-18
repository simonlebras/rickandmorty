package app.rickandmorty.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.window.layout.DisplayFeature
import app.rickandmorty.feature.home.navigation.homeRoute
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RamNavHost(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    NavHost(
        navController = navController,
        startDestination = homeRoute,
        modifier = modifier,
    ) {
        home(
            navController = navController,
            windowSizeClass = windowSizeClass,
            displayFeatures = displayFeatures,
        )

        settings(
            context = context,
            navController = navController,
        )
    }
}
