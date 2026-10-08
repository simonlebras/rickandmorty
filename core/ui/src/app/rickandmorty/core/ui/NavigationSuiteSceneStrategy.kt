package app.rickandmorty.core.ui

import androidx.compose.animation.EnterExitState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.get
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneDecoratorStrategy
import androidx.navigation3.scene.SceneDecoratorStrategyScope
import androidx.navigation3.ui.LocalNavAnimatedContentScope

private typealias NavigationSuiteContent = @Composable (Modifier, NavigationSuiteType) -> Unit

private class NavigationSuiteSceneStrategy<T : Any>(
  navigationSuiteState: NavigationSuiteState,
  onTopLevelRouteClick: State<(NavKey) -> Unit>,
) : SceneDecoratorStrategy<T> {
  private val navigationSuite: NavigationSuiteContent = { modifier, navigationSuiteType ->
    navigationSuiteState.NavigationSuite(
      onItemClick = { onTopLevelRouteClick.value(it) },
      modifier = modifier,
      navigationSuiteType = navigationSuiteType,
    )
  }
  private val movableNavigationSuite = movableContentOf(navigationSuite)

  override fun SceneDecoratorStrategyScope<T>.decorateScene(scene: Scene<T>): Scene<T> {
    val showNavigationSuite =
      scene.entries.any { entry -> entry.metadata[ShowNavigationSuiteKey] == true }
    return if (showNavigationSuite) {
      NavigationSuiteSceneDecorator(
        scene = scene,
        navigationSuite = navigationSuite,
        movableNavigationSuite = movableNavigationSuite,
      )
    } else {
      scene
    }
  }
}

private class NavigationSuiteSceneDecorator<T : Any>(
  private val scene: Scene<T>,
  private val navigationSuite: NavigationSuiteContent,
  private val movableNavigationSuite: NavigationSuiteContent,
) : Scene<T> by scene {
  override val content: @Composable () -> Unit = {
    val navigationSuiteType =
      NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfoV2())
    NestedNavigationSuiteScaffold(
      navigationSuite = {
        val canUseMoveableNavigationSuite =
          LocalNavAnimatedContentScope.current.transition.targetState == EnterExitState.Visible
        if (canUseMoveableNavigationSuite) {
          movableNavigationSuite(Modifier, navigationSuiteType)
        } else {
          navigationSuite(Modifier, navigationSuiteType)
        }
      },
      navigationSuiteType = navigationSuiteType,
      containerColor = Color.Transparent,
    ) {
      scene.content()
    }
  }
}

@Composable
public fun <T : Any> rememberNavigationSuiteSceneStrategy(
  navigationSuiteState: NavigationSuiteState,
  onTopLevelRouteClick: (NavKey) -> Unit,
): SceneDecoratorStrategy<T> {
  val currentOnTopLevelRouteClick = rememberUpdatedState(onTopLevelRouteClick)
  return remember(navigationSuiteState) {
    NavigationSuiteSceneStrategy(navigationSuiteState, currentOnTopLevelRouteClick)
  }
}
