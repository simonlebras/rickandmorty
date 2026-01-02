package app.rickandmorty.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
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
import app.rickandmorty.ui.character.navigation.CharacterListNavKey
import app.rickandmorty.ui.episode.navigation.EpisodeListNavKey
import app.rickandmorty.ui.location.navigation.LocationListNavKey
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentSet
import org.jetbrains.compose.resources.StringResource

object TopLevelNavigation {
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

data class TopLevelDestination(
  val route: NavKey,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val label: StringResource,
)
