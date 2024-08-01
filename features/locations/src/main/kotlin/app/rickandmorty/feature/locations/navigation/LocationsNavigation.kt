package app.rickandmorty.feature.locations.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.locations.list.LocationListScreen
import kotlinx.serialization.Serializable

@Serializable
public data object LocationList

public fun NavHostController.navigateToLocationList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = LocationList,
        builder = builder,
    )
}

public fun NavGraphBuilder.locationList(
    onNavigateToSettings: () -> Unit,
) {
    composable<LocationList> {
        LocationListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
