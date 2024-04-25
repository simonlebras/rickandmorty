package app.rickandmorty.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.feature.characters.navigation.characterListRoute
import app.rickandmorty.feature.episodes.navigation.episodeListRoute
import app.rickandmorty.feature.locations.navigation.locationListRoute

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int,
) {
    CharacterList(
        route = characterListRoute,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = L10nR.string.character_list_title,
    ),
    EpisodeList(
        route = episodeListRoute,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = L10nR.string.episode_list_title,
    ),
    LocationList(
        route = locationListRoute,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = L10nR.string.location_list_title,
    ),
}
