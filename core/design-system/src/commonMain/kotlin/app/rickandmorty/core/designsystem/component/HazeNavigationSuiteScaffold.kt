package app.rickandmorty.core.designsystem.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
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

@OptIn( ExperimentalLayoutApi::class)
@Composable
public fun HazeNavigationSuiteScaffold(
  navigationItems: @Composable () -> Unit,
  visibilityScope: AnimatedVisibilityScope,
  transitionScope: SharedTransitionScope,
  navigationSuiteType: NavigationSuiteType,
  modifier: Modifier = Modifier,
  layoutType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo()),
  navigationSuiteColors: NavigationSuiteColors =
    NavigationSuiteDefaults.colors(
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

  Surface(
    modifier =
      modifier.onConsumedWindowInsetsChanged { consumedWindowInsets ->
        // Exclude currently consumed window insets from user provided contentWindowInsets
        insets.insets = contentWindowInsets.exclude(consumedWindowInsets)
      },
    color = Color.Transparent,
    contentColor = Color.Transparent,
  ) {
    HazeNavigationSuiteScaffoldLayout(
      navigationSuite = {
        Box(
          Modifier.navigationSuiteScaffoldSharedElement(
              navigationSuiteType,
              visibilityScope,
              transitionScope,
            )
        ) {
          navigationItems()
        }
      },
      layoutType = layoutType,
      contentWindowInsets = insets,
    ) { contentPadding ->
      Box() {
        val contentPaddingMinusInsets =
          contentPadding.minus(
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

@Composable
private fun HazeNavigationSuiteScaffoldLayout(
  navigationSuite: @Composable () -> Unit,
  layoutType: NavigationSuiteType,
  contentWindowInsets: WindowInsets,
  content: @Composable (PaddingValues) -> Unit = {},
) {
  SubcomposeLayout { constraints ->
    val looseConstraints = constraints.copy(minWidth = 0, minHeight = 0)

    val navigationSuitePlaceables =
      subcompose(
          slotId = HazeNavigationSuiteScaffoldLayoutContent.NavigationSuite,
          content = navigationSuite,
        )
        .fastMap { it.measure(looseConstraints) }

    val navigationSuiteWidth = navigationSuitePlaceables.fastMaxBy { it.width }?.width ?: 0
    val navigationSuiteHeight = navigationSuitePlaceables.fastMaxBy { it.height }?.height ?: 0

    val isNavigationBar = layoutType == NavigationSuiteType.NavigationBar

    val contentPlaceables =
      subcompose(slotId = HazeNavigationSuiteScaffoldLayoutContent.Content) {
          val insets = contentWindowInsets.asPaddingValues(this@SubcomposeLayout)
          val innerPadding =
            PaddingValues(
              top = insets.calculateTopPadding(),
              bottom =
                if (isNavigationBar) {
                  navigationSuiteHeight.toDp()
                } else {
                  insets.calculateBottomPadding()
                },
              start =
                if (isNavigationBar) {
                  insets.calculateStartPadding((this@SubcomposeLayout).layoutDirection)
                } else {
                  navigationSuiteWidth.toDp()
                },
              end = insets.calculateEndPadding((this@SubcomposeLayout).layoutDirection),
            )
          content(innerPadding)
        }
        .fastMap { it.measure(looseConstraints) }

    val layoutWidth = constraints.maxWidth
    val layoutHeight = constraints.maxHeight
    layout(layoutWidth, layoutHeight) {
      // Placing to control drawing order to match default elevation of each placeable
      contentPlaceables.fastForEach { it.place(0, 0) }

      if (isNavigationBar) {
        // Place the navigation component at the bottom of the screen
        navigationSuitePlaceables.fastForEach {
          it.place(0, layoutHeight - (navigationSuiteHeight))
        }
      } else {
        // Place the navigation component at the start of the screen
        navigationSuitePlaceables.fastForEach { it.placeRelative(0, 0) }
      }
    }
  }
}

private enum class HazeNavigationSuiteScaffoldLayoutContent {
  Content,
  NavigationSuite,
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
private fun Modifier.navigationSuiteScaffoldSharedElement(
  navigationSuiteType: NavigationSuiteType,
  visibilityScope: AnimatedVisibilityScope,
  transitionScope: SharedTransitionScope,
): Modifier =
  with(transitionScope) {
    with(visibilityScope) {
      if (true) {
        then(
          animateEnterExit(enter = NavigationBarTransitionEnter, exit = NavigationBarTransitionExit)
            .sharedElement(
              animatedVisibilityScope = visibilityScope,
              sharedContentState = rememberSharedContentState(NavigationBarSharedElementKey),
            )
            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
        )
      } else if (navigationSuiteType.isNavigationRail) {
        then(
          animateEnterExit(
              enter = NavigationRailTransitionEnter,
              exit = NavigationRailTransitionExit,
            )
            .sharedElement(
              animatedVisibilityScope = visibilityScope,
              sharedContentState = rememberSharedContentState(NavigationRailSharedElementKey),
            )
            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
        )
      } else {
        Modifier
      }
    }
  }

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val NavigationBarTransitionEnter
  @Composable
  get() =
    slideInVertically(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      initialOffsetY = { it },
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val NavigationBarTransitionExit
  @Composable
  get() =
    slideOutVertically(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      targetOffsetY = { it },
    )

internal data object NavigationBarSharedElementKey

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val NavigationRailTransitionEnter
  @Composable
  get() =
    slideInHorizontally(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      initialOffsetX = { -it },
    )

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
internal val NavigationRailTransitionExit
  @Composable
  get() =
    slideOutHorizontally(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      targetOffsetX = { -it },
    )

internal data object NavigationRailSharedElementKey

public val NavigationSuiteType.isNavigationRail: Boolean
  get() =
    this == NavigationSuiteType.WideNavigationRailCollapsed ||
      this == NavigationSuiteType.WideNavigationRailExpanded ||
      this == NavigationSuiteType.NavigationRail
