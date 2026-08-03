package app.rickandmorty.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_list_title
import app.rickandmorty.core.l10n.resources.episode_list_title
import app.rickandmorty.core.l10n.resources.location_list_title
import app.rickandmorty.core.navigation.EntryProvider
import app.rickandmorty.core.navigation.NavigationState
import app.rickandmorty.core.ui.NavigationSuiteState
import app.rickandmorty.core.ui.TopLevelDestination
import app.rickandmorty.ui.character.navigation.CharacterListNavKey
import app.rickandmorty.ui.episode.navigation.EpisodeListNavKey
import app.rickandmorty.ui.location.navigation.LocationListNavKey
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentSet

@Stable
class RamAppState(val entryProvider: EntryProvider, val navigationState: NavigationState) :
  NavigationSuiteState {
  val currentEntries: ImmutableList<NavEntry<NavKey>>
    @Composable get() = navigationState.toDecoratedEntries(entryProvider)

  override val topLevelDestinations = TopLevelNavigations.destinations

  override val topLevelRoute: NavKey
    get() = navigationState.topLevelRoute
}

@Composable
fun rememberRamAppState(
  entryProvider: EntryProvider,
  navigationState: NavigationState,
): RamAppState {
  return remember(entryProvider, navigationState) {
    RamAppState(
      entryProvider = entryProvider,
      navigationState = navigationState,
    )
  }
}

object TopLevelNavigations {
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
