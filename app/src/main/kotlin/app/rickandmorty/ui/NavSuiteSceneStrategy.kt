package app.rickandmorty.ui

import androidx.compose.animation.EnterExitState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.scene.Scene
import androidx.navigation3.scene.SceneStrategy
import androidx.navigation3.scene.SceneStrategyScope
import androidx.navigation3.ui.LocalNavAnimatedContentScope
import app.rickandmorty.core.ui.ShowNavSuiteKey

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
class NavSuiteSceneStrategy<T : Any>(private val appState: RamAppState) : SceneStrategy<T> {
  override fun SceneStrategyScope<T>.calculateScene(entries: List<NavEntry<T>>): Scene<T>? = null

  override fun SceneStrategyScope<T>.decorateScene(scene: Scene<T>): Scene<T> {
    val hasBackNavSuite =
      scene.previousEntries
        .findLast { ShowNavSuiteKey in it.metadata }
        ?.metadata
        ?.get(ShowNavSuiteKey) == true
    val showNavSuite =
      scene.entries.findLast { ShowNavSuiteKey in it.metadata }?.metadata?.get(ShowNavSuiteKey) ==
        true
    return if (showNavSuite) {
      NavSuiteWrapper(appState = appState, scene = scene, hasBackNavSuite = hasBackNavSuite)
    } else {
      scene
    }
  }
}

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun <T : Any> rememberNavSuiteSceneStrategy(appState: RamAppState): NavSuiteSceneStrategy<T> {
  return remember(appState) { NavSuiteSceneStrategy(appState) }
}

private class NavSuiteWrapper<T : Any>(
  private val appState: RamAppState,
  private val scene: Scene<T>,
  private val hasBackNavSuite: Boolean,
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
