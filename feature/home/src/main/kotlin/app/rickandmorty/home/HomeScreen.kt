package app.rickandmorty.home

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayoutNav
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneMode

@Composable
internal fun HomeScreen(
    navController: NavHostController,
    singlePaneStartDestination: String,
    pane1StartDestination: String,
    pane2StartDestination: String,
    navGraph: NavGraphBuilder.() -> Unit,
) {
    TwoPaneLayoutNav(
        navController = navController,
        paneMode = TwoPaneMode.HorizontalSingle,
        singlePaneStartDestination = singlePaneStartDestination,
        pane1StartDestination = pane1StartDestination,
        pane2StartDestination = pane2StartDestination,
    ) {
        navGraph()
    }
}
