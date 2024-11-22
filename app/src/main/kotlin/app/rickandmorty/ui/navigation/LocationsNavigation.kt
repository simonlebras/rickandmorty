package app.rickandmorty.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.ui.location.list.LocationListScreen
import kotlinx.serialization.Serializable

@Serializable
data object LocationList

fun NavHostController.navigateToLocationList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = LocationList,
        builder = builder,
    )
}

fun NavGraphBuilder.locationList(
    onNavigateToSettings: () -> Unit,
) {
    composable<LocationList> {
        LocationListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
