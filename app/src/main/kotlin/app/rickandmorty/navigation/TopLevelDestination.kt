package app.rickandmorty.navigation

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.characters.R as CharactersR
import app.rickandmorty.characters.charactersRoute
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.episodes.R as EpisodesR
import app.rickandmorty.episodes.episodesRoute
import app.rickandmorty.locations.R as LocationsR
import app.rickandmorty.locations.locationsRoute

enum class TopLevelDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    @StringRes val label: Int,
) {
    CHARACTERS(
        route = charactersRoute,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = CharactersR.string.characters_title,
    ),
    EPISODES(
        route = episodesRoute,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = EpisodesR.string.episodes_title,
    ),
    LOCATIONS(
        route = locationsRoute,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = LocationsR.string.locations_title,
    ),
}
