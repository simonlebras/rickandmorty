package app.rickandmorty.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import app.rickandmorty.ui.navigation.RamNavHost
import app.rickandmorty.ui.navigation.TopLevelDestination

@Composable
fun RamApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val currentNavBackStackEntry = navController.currentBackStackEntryAsState().value
    val currentDestination = currentNavBackStackEntry?.destination
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
        modifier = modifier,
    ) {
        RamNavHost(navController = navController)
    }
}

private fun TopLevelDestination.isInHierarchy(destination: NavDestination?): Boolean = destination
    ?.hierarchy
    ?.any {
        it.hasRoute(route::class)
    } ?: false

private fun NavHostController.navigateToTopLevelDestination(destination: TopLevelDestination) {
    navigate(destination.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
