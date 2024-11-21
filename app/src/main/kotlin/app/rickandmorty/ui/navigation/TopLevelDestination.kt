package app.rickandmorty.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_list_title
import app.rickandmorty.core.l10n.resources.episode_list_title
import app.rickandmorty.core.l10n.resources.location_list_title
import app.rickandmorty.ui.navigation.CharacterList as CharacterListRoute
import app.rickandmorty.ui.navigation.EpisodeList as EpisodeListRoute
import app.rickandmorty.ui.navigation.LocationList as LocationListRoute
import org.jetbrains.compose.resources.StringResource

enum class TopLevelDestination(
    val route: Any,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val label: StringResource,
) {
    CharacterList(
        route = CharacterListRoute,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = L10nRes.string.character_list_title,
    ),
    EpisodeList(
        route = EpisodeListRoute,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = L10nRes.string.episode_list_title,
    ),
    LocationList(
        route = LocationListRoute,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = L10nRes.string.location_list_title,
    ),
}
