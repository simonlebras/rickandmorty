package app.rickandmorty.core.ui

import androidx.compose.animation.EnterExitState
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.get
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneDecoratorStrategy
import androidx.navigation3.scene.SceneDecoratorStrategyScope
import androidx.navigation3.ui.LocalNavAnimatedContentScope

private class NavigationSuiteSceneDecoratorStrategy<T : Any>(
  private val navigationSuiteState: NavigationSuiteState
) : SceneDecoratorStrategy<T> {
  override fun SceneDecoratorStrategyScope<T>.decorateScene(scene: Scene<T>): Scene<T> {
    val showNavigationSuite =
      scene.entries.any { entry -> entry.metadata[ShowNavigationSuiteKey] == true }
    return if (showNavigationSuite) {
      NavigationSuiteSceneDecorator(scene = scene, navigationSuiteState = navigationSuiteState)
    } else {
      scene
    }
  }
}

private class NavigationSuiteSceneDecorator<T : Any>(
  private val scene: Scene<T>,
  private val navigationSuiteState: NavigationSuiteState,
) : Scene<T> by scene {
  override val content: @Composable () -> Unit = {
    val navigationSuiteType =
      NavigationSuiteScaffoldDefaults.calculateFromAdaptiveInfo(currentWindowAdaptiveInfo())
    NestedNavigationSuiteScaffold(
      navigationSuite = {
        val canUseMoveableNavSuite =
          LocalNavAnimatedContentScope.current.transition.targetState == EnterExitState.Visible
        if (canUseMoveableNavSuite) {
          navigationSuiteState.movableNavigationSuite(Modifier, navigationSuiteType)
        } else {
          navigationSuiteState.NavigationSuite(navigationSuiteType = navigationSuiteType)
        }
      },
      navigationSuiteType = navigationSuiteType,
    ) {
      scene.content()
    }
  }
}

@Composable
public fun <T : Any> rememberNavSuiteSceneStrategy(
  navigationSuiteState: NavigationSuiteState
): SceneDecoratorStrategy<T> {
  return remember(navigationSuiteState) {
    NavigationSuiteSceneDecoratorStrategy(navigationSuiteState)
  }
}
