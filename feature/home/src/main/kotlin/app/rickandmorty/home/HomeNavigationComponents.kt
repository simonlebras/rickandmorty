package app.rickandmorty.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun HomeBottomAppBar(
    navigationItems: ImmutableList<HomeNavigationItem>,
    currentDestination: NavDestination?,
    onNavigationItemClick: (HomeNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(modifier = modifier) {
        navigationItems.fastForEach { item ->
            val selected = item.isInHierarchy(currentDestination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigationItemClick(item) },
                icon = {
                    val icon = if (selected) {
                        item.selectedIcon
                    } else {
                        item.unselectedIcon
                    }
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                    )
                },
                label = { Text(text = stringResource(item.label)) },
            )
        }
    }
}

@Composable
internal fun HomeNavigationRail(
    navigationItems: ImmutableList<HomeNavigationItem>,
    currentDestination: NavDestination?,
    navigationContentPosition: NavigationContentPosition,
    onNavigationItemClick: (HomeNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        Layout(
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    navigationItems.fastForEach { item ->
                        val selected = item.isInHierarchy(currentDestination)
                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigationItemClick(item) },
                            icon = {
                                val icon = if (selected) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                )
                            },
                            label = { Text(text = stringResource(item.label)) },
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition),
        )
    }
}

@Composable
internal fun HomePermanentNavigationDrawer(
    navigationItems: ImmutableList<HomeNavigationItem>,
    currentDestination: NavDestination?,
    navigationContentPosition: NavigationContentPosition,
    onNavigationItemClick: (HomeNavigationItem) -> Unit,
    modifier: Modifier = Modifier,
) {
    PermanentDrawerSheet(
        modifier = modifier.sizeIn(
            minWidth = 200.dp,
            maxWidth = 300.dp,
        ),
    ) {
        Layout(
            content = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    navigationItems.fastForEach { item ->
                        val selected = item.isInHierarchy(currentDestination)
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(item.label)) },
                            selected = selected,
                            onClick = { onNavigationItemClick(item) },
                            icon = {
                                val icon = if (selected) {
                                    item.selectedIcon
                                } else {
                                    item.unselectedIcon
                                }
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                )
                            },
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition),
        )
    }
}

private fun HomeNavigationItem.isInHierarchy(destination: NavDestination?): Boolean {
    return destination
        ?.hierarchy
        ?.any {
            it.route?.contains(route, true) ?: false
        } ?: false
}

private fun navigationMeasurePolicy(
    navigationContentPosition: NavigationContentPosition,
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        check(measurables.size == 1)
        val contentPlaceable = measurables.first().measure(constraints)
        layout(contentPlaceable.width, contentPlaceable.height) {
            val contentPlaceableY = when (navigationContentPosition) {
                NavigationContentPosition.TOP -> 0
                NavigationContentPosition.CENTER -> {
                    val nonContentVerticalSpace = constraints.maxHeight - contentPlaceable.height
                    nonContentVerticalSpace / 2
                }
            }
            contentPlaceable.placeRelative(0, contentPlaceableY)
        }
    }
}

/**
 * Different type of app navigation depending on device size and state.
 */
internal enum class NavigationType {
    BOTTOM_APP_BAR,
    NAVIGATION_RAIL,
    PERMANENT_NAVIGATION_DRAWER,
}

/**
 * Different position of navigation content inside navigation rail and navigation drawer
 * depending on device size and state.
 */
internal enum class NavigationContentPosition {
    TOP,
    CENTER,
}
