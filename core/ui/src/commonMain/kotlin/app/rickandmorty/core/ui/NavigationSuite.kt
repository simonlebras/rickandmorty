package app.rickandmorty.core.ui

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.WideNavigationRailDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuite
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteItem
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.navigation.LocalNavigator
import dev.chrisbanes.haze.hazeEffect
import org.jetbrains.compose.resources.stringResource

@Composable
public fun NavigationSuiteState.NavigationSuite(
  modifier: Modifier = Modifier,
  navigationSuiteType: NavigationSuiteType =
    NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfoV2()),
) {
  Box(
    modifier =
      modifier
        .navigationSuiteSharedElement(navigationSuiteType = navigationSuiteType)
        .hazeEffect(state = LocalHazeState.current)
  ) {
    NavigationSuite(
      navigationSuiteType = navigationSuiteType,
      colors =
        NavigationSuiteDefaults.colors(
          shortNavigationBarContainerColor = Color.Transparent,
          wideNavigationRailColors =
            WideNavigationRailDefaults.colors(
              containerColor = Color.Transparent,
              modalContainerColor = Color.Transparent,
            ),
          navigationBarContainerColor = Color.Transparent,
          navigationRailContainerColor = Color.Transparent,
          navigationDrawerContainerColor = Color.Transparent,
        ),
      verticalArrangement = Arrangement.Center,
    ) {
      val navigator = LocalNavigator.current

      topLevelDestinations.forEach { item ->
        val isSelected = topLevelRoute == item.route
        NavigationSuiteItem(
          selected = isSelected,
          onClick = { navigator.navigate(item.route) },
          icon = {
            Icon(
              imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
              contentDescription = null,
            )
          },
          label = { Text(stringResource(item.label)) },
        )
      }
    }
  }
}

@Suppress("ComposeUnstableReceiver")
@Composable
private fun Modifier.navigationSuiteSharedElement(
  navigationSuiteType: NavigationSuiteType
): Modifier {
  val animatedVisibilityScope = LocalNavAnimatedContentScope.current
  val sharedTransitionScope = LocalSharedTransitionScope.current
  return with(animatedVisibilityScope) {
    with(sharedTransitionScope) {
      then(
        if (navigationSuiteType.isNavigationBar) {
          animateEnterExit(enter = NavigationBarTransitionEnter, exit = NavigationBarTransitionExit)
            .sharedElement(
              animatedVisibilityScope = animatedVisibilityScope,
              sharedContentState = rememberSharedContentState(NavigationBarSharedElementKey),
            )
            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
        } else if (navigationSuiteType.isNavigationRail) {
          animateEnterExit(
              enter = NavigationRailTransitionEnter,
              exit = NavigationRailTransitionExit,
            )
            .sharedElement(
              animatedVisibilityScope = animatedVisibilityScope,
              sharedContentState = rememberSharedContentState(NavigationRailSharedElementKey),
            )
            .renderInSharedTransitionScopeOverlay(zIndexInOverlay = 1f)
        } else {
          Modifier
        }
      )
    }
  }
}

private data object NavigationBarSharedElementKey

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val NavigationBarTransitionEnter
  @Composable
  get() =
    slideInVertically(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      initialOffsetY = { it },
    )
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val NavigationBarTransitionExit
  @Composable
  get() =
    slideOutVertically(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      targetOffsetY = { it },
    )

private data object NavigationRailSharedElementKey

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val NavigationRailTransitionEnter
  @Composable
  get() =
    slideInHorizontally(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      initialOffsetX = { -it },
    )
@OptIn(ExperimentalMaterial3ExpressiveApi::class)
private val NavigationRailTransitionExit
  @Composable
  get() =
    slideOutHorizontally(
      animationSpec = MaterialTheme.motionScheme.defaultSpatialSpec(),
      targetOffsetX = { -it },
    )
