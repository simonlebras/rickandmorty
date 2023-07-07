package app.rickandmorty.home

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.window.layout.DisplayFeature
import androidx.window.layout.FoldingFeature
import app.rickandmorty.ui.DevicePosture
import app.rickandmorty.ui.empty
import app.rickandmorty.ui.isBookPosture
import app.rickandmorty.ui.isSeparating
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
internal fun HomeNavigationScreen(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
    navigationItems: ImmutableList<HomeNavigationItem>,
    navGraph: NavGraphBuilder.() -> Unit,
    modifier: Modifier = Modifier,
) {
    val foldingFeature = displayFeatures.filterIsInstance<FoldingFeature>().firstOrNull()
    val foldingDevicePosture = when {
        foldingFeature.isBookPosture() -> {
            DevicePosture.BookPosture(hingePosition = foldingFeature.bounds)
        }

        foldingFeature.isSeparating() -> {
            DevicePosture.Separating(
                hingePosition = foldingFeature.bounds,
                orientation = foldingFeature.orientation,
            )
        }

        else -> DevicePosture.NormalPosture
    }

    val navigationType = when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> NavigationType.BOTTOM_APP_BAR
        WindowWidthSizeClass.Medium -> NavigationType.NAVIGATION_RAIL
        WindowWidthSizeClass.Expanded -> {
            if (foldingDevicePosture is DevicePosture.BookPosture) {
                NavigationType.NAVIGATION_RAIL
            } else {
                NavigationType.PERMANENT_NAVIGATION_DRAWER
            }
        }

        else -> NavigationType.BOTTOM_APP_BAR
    }

    val navigationContentPosition = when (windowSizeClass.heightSizeClass) {
        WindowHeightSizeClass.Compact -> NavigationContentPosition.TOP
        WindowHeightSizeClass.Medium,
        WindowHeightSizeClass.Expanded,
        -> NavigationContentPosition.CENTER

        else -> NavigationContentPosition.TOP
    }

    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val firstNavigationItem = navigationItems.first()
    BackHandler(
        enabled = currentDestination?.route != firstNavigationItem.route,
    ) {
        navController.navigateToItem(firstNavigationItem)
    }

    HomeNavigationScreen(
        navigationType = navigationType,
        navigationContentPosition = navigationContentPosition,
        navigationItems = navigationItems,
        currentDestination = currentDestination,
        onNavigateToItem = navController::navigateToItem,
        modifier = modifier,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = firstNavigationItem.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .consumeWindowInsets(padding),
        ) {
            navGraph()
        }
    }
}

@Composable
private fun HomeNavigationScreen(
    navigationType: NavigationType,
    navigationContentPosition: NavigationContentPosition,
    navigationItems: ImmutableList<HomeNavigationItem>,
    currentDestination: NavDestination?,
    onNavigateToItem: (HomeNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    HomeNavigationScaffold(
        modifier = modifier.fillMaxSize(),
        startBar = {
            AnimatedVisibility(
                visible = navigationType == NavigationType.NAVIGATION_RAIL ||
                    navigationType == NavigationType.PERMANENT_NAVIGATION_DRAWER,
                enter = slideInHorizontally { -it },
                exit = slideOutHorizontally { -it },
                label = "startBar",
            ) {
                AnimatedContent(
                    targetState = navigationType,
                    label = "startBarContent",
                ) { targetState ->
                    when (targetState) {
                        NavigationType.PERMANENT_NAVIGATION_DRAWER -> {
                            HomePermanentNavigationDrawer(
                                navigationItems = navigationItems,
                                currentDestination = currentDestination,
                                navigationContentPosition = navigationContentPosition,
                                onNavigationItemClick = onNavigateToItem,
                            )
                        }

                        else -> {
                            HomeNavigationRail(
                                navigationItems = navigationItems,
                                currentDestination = currentDestination,
                                navigationContentPosition = navigationContentPosition,
                                onNavigationItemClick = onNavigateToItem,
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            AnimatedVisibility(
                visible = navigationType == NavigationType.BOTTOM_APP_BAR,
                enter = slideInVertically { it },
                exit = slideOutVertically { it },
                label = "bottomBar",
            ) {
                HomeBottomAppBar(
                    navigationItems = navigationItems,
                    currentDestination = currentDestination,
                    onNavigationItemClick = onNavigateToItem,
                )
            }
        },
        contentWindowInsets = WindowInsets.empty,
    ) { padding ->
        content(padding)
    }
}

private fun NavHostController.navigateToItem(item: HomeNavigationItem) {
    navigate(item.route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
