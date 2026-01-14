package app.rickandmorty.ui

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import app.rickandmorty.core.designsystem.theme.LocalHazeState
import app.rickandmorty.core.designsystem.theme.LocalSharedTransitionScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.Navigator
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RamApp(appState: RamAppState, modifier: Modifier = Modifier) {
  val hazeState = rememberHazeState()

  val navigationState = appState.navigationState
  val navigator = remember(navigationState) { Navigator(navigationState) }

  val listDetailSceneStrategy = rememberListDetailSceneStrategy<NavKey>()
  val navSuiteSceneStrategy = rememberNavSuiteSceneStrategy<NavKey>(appState)

  CompositionLocalProvider(LocalHazeState provides hazeState, LocalNavigator provides navigator) {
    NavDisplay(
      entries = appState.currentEntries,
      modifier = modifier.hazeSource(hazeState),
      sceneStrategy = listDetailSceneStrategy then navSuiteSceneStrategy,
      sharedTransitionScope = LocalSharedTransitionScope.current,
      onBack = navigator::goBack,
    )
  }
}
