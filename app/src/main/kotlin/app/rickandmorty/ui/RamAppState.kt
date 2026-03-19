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
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.filled.Face
import app.rickandmorty.core.designsystem.icon.filled.Map
import app.rickandmorty.core.designsystem.icon.filled.Tv
import app.rickandmorty.core.designsystem.icon.outlined.Face
import app.rickandmorty.core.designsystem.icon.outlined.Map
import app.rickandmorty.core.designsystem.icon.outlined.Tv
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_list_title
import app.rickandmorty.core.l10n.resources.episode_list_title
import app.rickandmorty.core.l10n.resources.location_list_title
import app.rickandmorty.core.navigation.EntryProvider
import app.rickandmorty.core.navigation.NavigationState
import app.rickandmorty.core.navigation.rememberNavigationState
import app.rickandmorty.core.ui.NavigationSuite
import app.rickandmorty.core.ui.NavigationSuiteState
import app.rickandmorty.core.ui.TopLevelDestination
import app.rickandmorty.ui.character.navigation.CharacterListNavKey
import app.rickandmorty.ui.episode.navigation.EpisodeListNavKey
import app.rickandmorty.ui.location.navigation.LocationListNavKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentSet

@Stable
class RamAppState(val navigationState: NavigationState, val entryProvider: EntryProvider) :
  NavigationSuiteState {
  val currentEntries: ImmutableList<NavEntry<NavKey>>
    @Composable get() = navigationState.toDecoratedEntries(entryProvider)

  override val topLevelDestinations = TopLevelNavigations.destinations

  override val topLevelRoute: NavKey
    get() = navigationState.topLevelRoute

  override val movableNavigationSuite =
    movableContentOf<Modifier, NavigationSuiteType> { modifier, navigationSuiteType ->
      NavigationSuite(modifier = modifier, navigationSuiteType = navigationSuiteType)
    }
}

@Composable
fun rememberRamAppState(
  entryProvider: EntryProvider,
  savedStateConfiguration: SavedStateConfiguration,
): RamAppState {
  val navigationState =
    rememberNavigationState(
      startRoute = TopLevelNavigations.startRoute,
      topLevelRoutes = TopLevelNavigations.routes,
      configuration = savedStateConfiguration,
    )

  return remember(navigationState, entryProvider) {
    RamAppState(navigationState = navigationState, entryProvider = entryProvider)
  }
}

private object TopLevelNavigations {
  val destinations =
    persistentListOf(
      TopLevelDestination(
        route = CharacterListNavKey,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = L10nRes.string.character_list_title,
      ),
      TopLevelDestination(
        route = EpisodeListNavKey,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = L10nRes.string.episode_list_title,
      ),
      TopLevelDestination(
        route = LocationListNavKey,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = L10nRes.string.location_list_title,
      ),
    )

  val routes = destinations.map { it.route }.toPersistentSet()

  val startRoute: NavKey = CharacterListNavKey
}
