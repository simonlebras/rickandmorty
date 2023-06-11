package app.rickandmorty.locations

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

public const val locationsRoute: String = "locations"

public fun NavController.navigateToLocations(navOptions: NavOptions? = null) {
    navigate(locationsRoute, navOptions)
}

public fun NavGraphBuilder.locations() {
    composable(route = locationsRoute) {
        LocationsScreen()
    }
}
