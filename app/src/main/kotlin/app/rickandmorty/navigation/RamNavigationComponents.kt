package app.rickandmorty.navigation

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
import app.rickandmorty.utils.NavigationContentPosition
import kotlinx.collections.immutable.ImmutableList

@Composable
fun RamBottomAppBar(
    destinations: ImmutableList<TopLevelDestination>,
    currentDestination: NavDestination?,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    BottomAppBar(modifier = modifier) {
        destinations.fastForEach { destination ->
            val selected = destination.isInHierarchy(currentDestination)
            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
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
            )
        }
    }
}

@Composable
fun RamNavigationRail(
    destinations: ImmutableList<TopLevelDestination>,
    currentDestination: NavDestination?,
    navigationContentPosition: NavigationContentPosition,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    NavigationRail(modifier = modifier) {
        Layout(
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    destinations.fastForEach { destination ->
                        val selected = destination.isInHierarchy(currentDestination)
                        NavigationRailItem(
                            selected = selected,
                            onClick = { onNavigateToDestination(destination) },
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
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition),
        )
    }
}

@Composable
fun RamPermanentNavigationDrawer(
    destinations: ImmutableList<TopLevelDestination>,
    currentDestination: NavDestination?,
    navigationContentPosition: NavigationContentPosition,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    modifier: Modifier = Modifier,
) {
    PermanentDrawerSheet(modifier = modifier.sizeIn(minWidth = 200.dp, maxWidth = 300.dp)) {
        Layout(
            content = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    destinations.fastForEach { destination ->
                        val selected = destination.isInHierarchy(currentDestination)
                        NavigationDrawerItem(
                            label = { Text(text = stringResource(destination.label)) },
                            selected = selected,
                            onClick = { onNavigateToDestination(destination) },
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
                        )
                    }
                }
            },
            measurePolicy = navigationMeasurePolicy(navigationContentPosition),
        )
    }
}

private fun navigationMeasurePolicy(
    navigationContentPosition: NavigationContentPosition,
): MeasurePolicy {
    return MeasurePolicy { measurables, constraints ->
        check(measurables.size == 1)
        val contentPlaceable = measurables.first().measure(constraints)
        layout(constraints.maxWidth, constraints.maxHeight) {
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
