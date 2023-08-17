package app.rickandmorty.feature.locations.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.locations.details.LocationDetailsScreen
import app.rickandmorty.feature.locations.list.LocationListScreen
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneNavScope
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable as twopanelayoutComposable

public const val locationListRoute: String = "locationList"
private const val locationDetailsRoute = "locationDetails"

public fun NavHostController.navigateToLocationList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = locationListRoute,
        builder = builder,
    )
}

context(TwoPaneNavScope)
public fun NavHostController.navigateToLocationDetails(
    launchScreen: Screen,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    navigateTo(
        route = locationDetailsRoute,
        launchScreen = launchScreen,
        builder = builder,
    )
}

public fun NavGraphBuilder.locationList() {
    composable(route = locationListRoute) {
        LocationListScreen()
    }
}

public fun NavGraphBuilder.locationDetails() {
    twopanelayoutComposable(route = locationDetailsRoute) {
        LocationDetailsScreen()
    }
}
