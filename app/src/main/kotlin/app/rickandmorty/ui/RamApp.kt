package app.rickandmorty.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.material3.adaptive.layout.calculatePaneScaffoldDirective
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.ui.LocalHazeState
import app.rickandmorty.core.ui.rememberNavigationSuiteSceneStrategy
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RamApp(
  appState: RamAppState,
  onTopLevelRouteClick: (NavKey) -> Unit,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val hazeState = rememberHazeState()

  val windowAdaptiveInfo = currentWindowAdaptiveInfoV2()
  val directive =
    remember(windowAdaptiveInfo) {
      calculatePaneScaffoldDirective(windowAdaptiveInfo).copy(horizontalPartitionSpacerSize = 0.dp)
    }
  val listDetailStrategy = rememberListDetailSceneStrategy<NavKey>(directive = directive)

  val navigationSuiteStrategy =
    rememberNavigationSuiteSceneStrategy<NavKey>(
      navigationSuiteState = appState,
      onTopLevelRouteClick = onTopLevelRouteClick,
    )

  CompositionLocalProvider(LocalHazeState provides hazeState) {
    NavDisplay(
      entries = appState.currentEntries,
      modifier = modifier,
      sceneStrategies = [listDetailStrategy],
      sceneDecoratorStrategies = [navigationSuiteStrategy],
      sharedTransitionScope = LocalSharedTransitionScope.current,
      onBack = onBack,
    )
  }
}
