package app.rickandmorty

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import app.rickandmorty.navigation.RamNavHost
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RamApp(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    modifier: Modifier = Modifier,
) {
    RamNavHost(
        navController = navController,
        windowSizeClass = windowSizeClass,
        displayFeatures = displayFeatures,
        modifier = modifier.fillMaxSize(),
    )
}
