package app.rickandmorty.ui

import androidx.compose.animation.EnterExitState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneDecoratorStrategy
import androidx.navigation3.scene.SceneDecoratorStrategyScope
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import app.rickandmorty.core.ui.ShowNavSuiteKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
class NavSuiteSceneDecoratorStrategy<T : Any>(private val appState: RamAppState) :
  SceneDecoratorStrategy<T> {
  override fun SceneDecoratorStrategyScope<T>.decorateScene(scene: Scene<T>): Scene<T> {
    val showNavSuite =
      scene.entries.findLast { ShowNavSuiteKey in it.metadata }?.metadata?.get(ShowNavSuiteKey) ==
        true
    return if (showNavSuite) {
      NavSuiteWrapper(appState = appState, scene = scene)
    } else {
      scene
    }
  }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun <T : Any> rememberNavSuiteSceneStrategy(
  appState: RamAppState
): NavSuiteSceneDecoratorStrategy<T> {
  return remember(appState) { NavSuiteSceneDecoratorStrategy(appState) }
}

private class NavSuiteWrapper<T : Any>(
  private val appState: RamAppState,
  private val scene: Scene<T>,
) : Scene<T> {
  override val key = scene.key
  override val entries = scene.entries
  override val previousEntries = scene.previousEntries

  override val content: @Composable () -> Unit = {
    val canUseMoveableNavSuite =
      LocalNavAnimatedContentScope.current.transition.targetState == EnterExitState.Visible

    appState.AdaptiveNavigationSuiteScaffold(
      navigationItems = {
        if (canUseMoveableNavSuite) {
          appState.movableNavSuite(Modifier)
        } else {
          appState.NavSuite()
        }
      }
    ) {
      scene.content()
    }
  }
}
