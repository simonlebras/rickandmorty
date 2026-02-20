package app.rickandmorty.ui

import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteType
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.movableContentOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.savedstate.serialization.SavedStateConfiguration
import app.rickandmorty.core.navigation.EntryProvider
import app.rickandmorty.core.navigation.NavigationState
import app.rickandmorty.core.navigation.rememberNavigationState
import app.rickandmorty.navigation.TopLevelNavigation
import kotlinx.collections.immutable.PersistentList

@Stable
class RamAppState(val navigationState: NavigationState, val entryProvider: EntryProvider) {
  val currentEntries: PersistentList<NavEntry<NavKey>>
    @Composable get() = navigationState.toDecoratedEntries(entryProvider)

  val topLevelDestinations = TopLevelNavigation.destinations

  val topLevelRoute: NavKey
    get() = navigationState.topLevelRoute

   val movableNavSuite = movableContentOf< Modifier> {  modifier ->
    NavSuite(
      modifier = modifier,
    )
  }
}

@Composable
fun rememberRamAppState(
  entryProvider: EntryProvider,
  savedStateConfiguration: SavedStateConfiguration,
): RamAppState {
  val navigationState =
    rememberNavigationState(
      startRoute = TopLevelNavigation.startRoute,
      topLevelRoutes = TopLevelNavigation.routes,
      configuration = savedStateConfiguration,
    )

  return remember(navigationState, entryProvider) {
    RamAppState(navigationState = navigationState, entryProvider = entryProvider)
  }
}
