package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.MutableWindowInsets
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.onConsumedWindowInsetsChanged
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigation.suite.ExperimentalMaterial3AdaptiveNavigationSuiteApi
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuite
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteScope
import androidx.compose.material3.adaptive.navigation.suite.NavigationSuiteType
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.util.fastForEach
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.util.fastMaxBy
import app.rickandmorty.core.designsystem.component.util.LocalScaffoldContentPadding
import app.rickandmorty.core.designsystem.component.util.PaddingValuesInsets
import app.rickandmorty.core.designsystem.component.util.minus
import dev.chrisbanes.haze.HazeDefaults
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

@OptIn(
    ExperimentalHazeMaterialsApi::class,
    ExperimentalLayoutApi::class,
    ExperimentalMaterial3AdaptiveApi::class,
    ExperimentalMaterial3AdaptiveNavigationSuiteApi::class,
)
@Composable
public fun HazeNavigationSuiteScaffold(
    navigationSuiteItems: NavigationSuiteScope.() -> Unit,
    modifier: Modifier = Modifier,
    layoutType: NavigationSuiteType = NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(
        currentWindowAdaptiveInfo(),
    ),
    navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(
        navigationBarContainerColor = Color.Transparent,
        navigationRailContainerColor = Color.Transparent,
        navigationDrawerContainerColor = Color.Transparent,
    ),
    navigationSuiteContainerColor: Color = MaterialTheme.colorScheme.surface,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    contentWindowInsets: WindowInsets = ScaffoldDefaults.contentWindowInsets,
    content: @Composable (PaddingValues) -> Unit = {},
) {
    val upstreamContentPadding = LocalScaffoldContentPadding.current

    val insets = remember {
        MutableWindowInsets(contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding)))
    }

    val layoutDirection = LocalLayoutDirection.current

    LaunchedEffect(contentWindowInsets, upstreamContentPadding, layoutDirection) {
        insets.insets = contentWindowInsets.add(PaddingValuesInsets(upstreamContentPadding))
    }

    val hazeState = remember { HazeState() }

    Surface(
        modifier = modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
            // Exclude currently consumed window insets from user provided contentWindowInsets
            insets.insets = contentWindowInsets.exclude(consumedWindowInsets)
        },
        color = containerColor,
        contentColor = contentColor,
    ) {
        HazeNavigationSuiteScaffoldLayout(
            navigationSuite = {
                NavigationSuite(
                    modifier = Modifier.hazeChild(
                        state = hazeState,
                        style = HazeMaterials.thin(),
                    ),
                    layoutType = layoutType,
                    colors = navigationSuiteColors,
                    content = navigationSuiteItems,
                )
            },
            layoutType = layoutType,
            contentWindowInsets = insets,
        ) { contentPadding ->
            Box(
                modifier = Modifier.haze(
                    state = hazeState,
                    style = HazeDefaults.style(backgroundColor = navigationSuiteContainerColor),
                ),
            ) {
                val contentPaddingMinusInsets = contentPadding.minus(
                    padding = contentWindowInsets.asPaddingValues(),
                    layoutDirection = layoutDirection,
                )

                CompositionLocalProvider(LocalScaffoldContentPadding provides contentPaddingMinusInsets) {
                    content(contentPadding)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3AdaptiveNavigationSuiteApi::class)
@Composable
private fun HazeNavigationSuiteScaffoldLayout(
    navigationSuite: @Composable () -> Unit,
    layoutType: NavigationSuiteType,
    contentWindowInsets: WindowInsets,
    content: @Composable (PaddingValues) -> Unit = {},
) {
    SubcomposeLayout { constraints ->
        val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

        val navigationSuitePlaceables = subcompose(
            slotId = HazeNavigationSuiteScaffoldLayoutContent.NavigationSuite,
            content = navigationSuite,
        ).fastMap {
            it.measure(looseConstraints)
        }

        val navigationSuiteWidth = navigationSuitePlaceables.fastMaxBy { it.width }?.width ?: 0
        val navigationSuiteHeight = navigationSuitePlaceables.fastMaxBy { it.height }?.height ?: 0

        val isNavigationBar = layoutType == NavigationSuiteType.NavigationBar

        val contentPlaceables = subcompose(
            slotId = HazeNavigationSuiteScaffoldLayoutContent.Content,
        ) {
            val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
            val innerPadding = PaddingValues(
                top = insets.calculateTopPadding(),
                bottom = if (isNavigationBar) {
                    navigationSuiteHeight.toDp()
                } else {
                    insets.calculateBottomPadding()
                },
                start = if (isNavigationBar) {
                    insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection)
                } else {
                    navigationSuiteWidth.toDp()
                },
                end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection),
            )
            content(innerPadding)
        }.fastMap { it.measure(looseConstraints) }

        val layoutWidth = constraints.maxWidth
        val layoutHeight = constraints.maxHeight
        layout(layoutWidth, layoutHeight) {
            // Placing to control drawing order to match default elevation of each placeable
            contentPlaceables.fastForEach {
                it.place(0, 0)
            }

            if (isNavigationBar) {
                // Place the navigation component at the bottom of the screen
                navigationSuitePlaceables.fastForEach {
                    it.place(0, layoutHeight - (navigationSuiteHeight))
                }
            } else {
                // Place the navigation component at the start of the screen
                navigationSuitePlaceables.fastForEach {
                    it.placeRelative(0, 0)
                }
            }
        }
    }
}

private enum class HazeNavigationSuiteScaffoldLayoutContent {
    Content,
    NavigationSuite,
}
