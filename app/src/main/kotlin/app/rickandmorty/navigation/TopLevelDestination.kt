package app.rickandmorty.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.feature.characters.R as CharactersR
import app.rickandmorty.feature.characters.navigation.characterListRoute
import app.rickandmorty.feature.episodes.R as EpisodesR
import app.rickandmorty.feature.episodes.navigation.episodeListRoute
import app.rickandmorty.feature.locations.R as LocationsR
import app.rickandmorty.feature.locations.navigation.locationListRoute

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int,
) {
    CHARACTER_LIST(
        route = characterListRoute,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = CharactersR.string.character_list_title,
    ),
    EPISODE_LIST(
        route = episodeListRoute,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = EpisodesR.string.episode_list_title,
    ),
    LOCATION_LIST(
        route = locationListRoute,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = LocationsR.string.location_list_title,
    ),
}
