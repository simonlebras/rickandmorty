package app.rickandmorty.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.animateBounds
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.ExperimentalMaterial3ComponentOverrideApi
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationRailDefaults
import androidx.compose.material3.ShortNavigationBarDefaults
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteColors
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldState
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldValue
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import app.rickandmorty.core.designsystem.component.HazeNavigationSuiteScaffold
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope

/** @see androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold */
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalMaterial3ComponentOverrideApi::class)
@Composable
fun AnimatedNavigationSuiteScaffold(
  visibilityScope: AnimatedVisibilityScope,
  transitionScope: SharedTransitionScope,
  navigationItems: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  navigationSuiteType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.navigationSuiteType(currentWindowAdaptiveInfo()),
  navigationSuiteColors: NavigationSuiteColors = NavigationSuiteDefaults.colors(),
  navigationItemVerticalArrangement: Arrangement.Vertical =
    NavigationSuiteDefaults.verticalArrangement,
  primaryActionContent: @Composable () -> Unit = {},
  content: @Composable () -> Unit,
) {
  HazeNavigationSuiteScaffold(
    navigationItems = navigationItems,
    navigationSuiteType = navigationSuiteType,
    visibilityScope = visibilityScope,
    transitionScope = transitionScope,
    modifier = modifier.animateBounds(LocalSharedTransitionScope.current),
    content = { Box() { content() } },
  )
}

@Composable
private fun Modifier.navigationSuiteScaffoldConsumeWindowInsets(
  navigationSuiteType: NavigationSuiteType,
  state: NavigationSuiteScaffoldState,
): Modifier =
  consumeWindowInsets(
    if (state.currentValue == NavigationSuiteScaffoldValue.Hidden && !state.isAnimating) {
      NoWindowInsets
    } else {
      when (navigationSuiteType) {
        NavigationSuiteType.ShortNavigationBarCompact,
        NavigationSuiteType.ShortNavigationBarMedium ->
          ShortNavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)

        NavigationSuiteType.WideNavigationRailCollapsed,
        NavigationSuiteType.WideNavigationRailExpanded ->
          WideNavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)

        NavigationSuiteType.NavigationBar ->
          NavigationBarDefaults.windowInsets.only(WindowInsetsSides.Bottom)

        NavigationSuiteType.NavigationRail ->
          NavigationRailDefaults.windowInsets.only(WindowInsetsSides.Start)

        NavigationSuiteType.NavigationDrawer ->
          DrawerDefaults.windowInsets.only(WindowInsetsSides.Start)

        else -> NoWindowInsets
      }
    }
  )

private val NoWindowInsets = WindowInsets(0, 0, 0, 0)
