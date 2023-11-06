package app.rickandmorty

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import app.rickandmorty.navigation.RamNavHost
import app.rickandmorty.navigation.TopLevelDestination

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
fun RamApp(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    Box(modifier = modifier) {
        NavigationSuiteScaffold(
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
        ) {
            RamNavHost(navController = navController)
        }
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
