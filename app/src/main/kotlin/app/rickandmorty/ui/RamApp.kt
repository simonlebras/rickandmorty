package app.rickandmorty.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.rickandmorty.core.designsystem.component.HazeNavigationSuiteScaffold
import app.rickandmorty.ui.navigation.RamNavHost
import app.rickandmorty.ui.navigation.TopLevelDestination

@Composable
fun RamApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val layoutType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
        currentWindowAdaptiveInfo(),
    )
    HazeNavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelDestination.entries.fastForEach { destination ->
                val selected = destination.isInHierarchy(currentDestination)
                item(
                    selected = selected,
                    icon = {
                        val icon = if (selected) {
                            destination.selectedIcon
                        } else {
                            destination.unselectedIcon
                        }
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                        )
                    },
                    label = { Text(text = stringResource(destination.label)) },
                    onClick = { navController.navigateToTopLevelDestination(destination) },
                )
            }
        },
        layoutType = layoutType,
        modifier = modifier,
    ) { _ ->
        RamNavHost(navController = navController)
    }
}

private fun TopLevelDestination.isInHierarchy(destination: NavDestination?): Boolean {
    return destination
        ?.hierarchy
        ?.any {
            it.route?.contains(route, true) ?: false
        } ?: false
}

private fun NavHostController.navigateToTopLevelDestination(destination: TopLevelDestination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
