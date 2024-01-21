package app.rickandmorty.feature.locations.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.locations.list.LocationListScreen

public const val locationListRoute: String = "locationList"
private const val locationDetailsRoute = "locationDetails"

public fun NavHostController.navigateToLocationList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = locationListRoute,
        builder = builder,
    )
}

public fun NavGraphBuilder.locationList(
    onNavigateToSettings: () -> Unit,
) {
    composable(route = locationListRoute) {
        LocationListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
