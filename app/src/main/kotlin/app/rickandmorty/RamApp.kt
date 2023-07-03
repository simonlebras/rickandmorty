package app.rickandmorty

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LookaheadScope
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navOptions
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import app.rickandmorty.characters.navigateToCharacters
import app.rickandmorty.episodes.navigateToEpisodes
import app.rickandmorty.locations.navigateToLocations
import app.rickandmorty.navigation.RamBottomAppBar
import app.rickandmorty.navigation.RamNavHost
import app.rickandmorty.navigation.RamNavigationRail
import app.rickandmorty.navigation.RamPermanentNavigationDrawer
import app.rickandmorty.navigation.TopLevelDestination
import app.rickandmorty.navigation.TopLevelDestination.CHARACTERS
import app.rickandmorty.navigation.TopLevelDestination.EPISODES
import app.rickandmorty.navigation.TopLevelDestination.LOCATIONS
import app.rickandmorty.utils.ContentType
import app.rickandmorty.utils.DevicePosture
import app.rickandmorty.utils.NavigationContentPosition
import app.rickandmorty.utils.NavigationType
import app.rickandmorty.utils.isBookPosture
import app.rickandmorty.utils.isSeparating
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalLayoutApi::class, ExperimentalComposeUiApi::class)
@Composable
fun RamApp(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    modifier: Modifier = Modifier,
) {
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = when {
        isBookPosture(foldingFeature) -> {
            DevicePosture.BookPosture(hingePosition = foldingFeature.bounds)
        }

        isSeparating(foldingFeature) -> {
            DevicePosture.Separating(
                hingePosition = foldingFeature.bounds,
                orientation = foldingFeature.orientation,
            )
        }

        else -> DevicePosture.NormalPosture
    }

    val navigationType: NavigationType
    val contentType: ContentType
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            navigationType = NavigationType.BOTTOM_APP_BAR
            contentType = ContentType.SINGLE_PANE
        }

        WindowWidthSizeClass.Medium -> {
            navigationType = NavigationType.NAVIGATION_RAIL
            contentType = if (foldingDevicePosture != DevicePosture.NormalPosture) {
                ContentType.DUAL_PANE
            } else {
                ContentType.SINGLE_PANE
            }
        }

        WindowWidthSizeClass.Expanded -> {
            navigationType = if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
            contentType = ContentType.DUAL_PANE
        }

        else -> {
            navigationType = NavigationType.BOTTOM_APP_BAR
            contentType = ContentType.SINGLE_PANE
        }
    }

    val navigationContentPosition = when (windowSizeClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> {
            NavigationContentPosition.TOP
        }

        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded,
        -> {
            NavigationContentPosition.CENTER
        }

        else -> {
            NavigationContentPosition.TOP
        }
    }

    val topLevelDestinations = TopLevelDestination.values().toList().toImmutableList()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val showTopLevelNavigation = topLevelDestinations.any { it.route == currentDestination?.route }

    BackHandler(
        enabled = showTopLevelNavigation && currentDestination?.route != CHARACTERS.route,
    ) {
        navController.navigateToTopLevelDestination(CHARACTERS)
    }

    LookaheadScope {
        AppScaffold(
            modifier = modifier.fillMaxSize(),
            startBar = {
                AnimatedVisibility(
                    visible = showTopLevelNavigation &&
                        (
                            navigationType == NavigationType.NAVIGATION_RAIL ||
                                navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER
                            ),
                    enter = slideInHorizontally { -it },
                    exit = slideOutHorizontally { -it },
                    label = "sideNavigation",
                ) {
                    AnimatedContent(
                        targetState = navigationType,
                        label = "sideNavigationBar",
                    ) { targetState ->
                        when (targetState) {
                            NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                                RamPermanentNavigationDrawer(
                                    destinations = topLevelDestinations,
                                    currentDestination = currentDestination,
                                    navigationContentPosition = navigationContentPosition,
                                    onNavigateToDestination = navController::navigateToTopLevelDestination,
                                )
                            }

                            else -> {
                                RamNavigationRail(
                                    destinations = topLevelDestinations,
                                    currentDestination = currentDestination,
                                    navigationContentPosition = navigationContentPosition,
                                    onNavigateToDestination = navController::navigateToTopLevelDestination,
                                )
                            }
                        }
                    }
                }
            },
            bottomBar = {
                AnimatedVisibility(
                    visible = showTopLevelNavigation && navigationType == NavigationType.BOTTOM_APP_BAR,
                    enter = slideInVertically { it },
                    exit = slideOutVertically { it },
                    label = "bottomNavigation",
                ) {
                    RamBottomAppBar(
                        destinations = topLevelDestinations,
                        currentDestination = currentDestination,
                        onNavigateToDestination = navController::navigateToTopLevelDestination,
                    )
                }
            },
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) { padding ->
            RamNavHost(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .consumeWindowInsets(padding),
            )
        }
    }
}

private fun NavController.navigateToTopLevelDestination(destination: TopLevelDestination) {
    val navOptions = navOptions {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
    when (destination) {
        CHARACTERS -> navigateToCharacters(navOptions)
        EPISODES -> navigateToEpisodes(navOptions)
        LOCATIONS -> navigateToLocations(navOptions)
    }
}
