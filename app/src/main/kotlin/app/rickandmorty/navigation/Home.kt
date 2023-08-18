package app.rickandmorty.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.window.layout.DisplayFeature
import app.rickandmorty.designsystem.icon.RamIcons
import app.rickandmorty.feature.characters.R as CharactersR
import app.rickandmorty.feature.characters.navigation.characterDetails
import app.rickandmorty.feature.characters.navigation.characterDetailsRoute
import app.rickandmorty.feature.characters.navigation.characterList
import app.rickandmorty.feature.characters.navigation.characterListRoute
import app.rickandmorty.feature.characters.navigation.navigateToCharacterDetails
import app.rickandmorty.feature.episodes.R as EpisodesR
import app.rickandmorty.feature.episodes.navigation.episodeDetails
import app.rickandmorty.feature.episodes.navigation.episodeList
import app.rickandmorty.feature.episodes.navigation.episodeListRoute
import app.rickandmorty.feature.home.HomeNavigationItem
import app.rickandmorty.feature.home.navigation.home
import app.rickandmorty.feature.home.navigation.homeNavigation
import app.rickandmorty.feature.home.navigation.homeNavigationRoute
import app.rickandmorty.feature.locations.R as LocationsR
import app.rickandmorty.feature.locations.navigation.locationDetails
import app.rickandmorty.feature.locations.navigation.locationList
import app.rickandmorty.feature.locations.navigation.locationListRoute
import app.rickandmorty.feature.settings.navigation.navigateToMainSettings
import com.microsoft.device.dualscreen.twopanelayout.Screen
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private val homeNavigationItems = persistentListOf(
    HomeNavigationItem(
        route = characterListRoute,
        selectedIcon = RamIcons.Filled.Face,
        unselectedIcon = RamIcons.Outlined.Face,
        label = CharactersR.string.character_list_title,
    ),
    HomeNavigationItem(
        route = episodeListRoute,
        selectedIcon = RamIcons.Filled.Tv,
        unselectedIcon = RamIcons.Outlined.Tv,
        label = EpisodesR.string.episode_list_title,
    ),
    HomeNavigationItem(
        route = locationListRoute,
        selectedIcon = RamIcons.Filled.Map,
        unselectedIcon = RamIcons.Outlined.Map,
        label = LocationsR.string.location_list_title,
    ),
)

fun NavGraphBuilder.home(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    displayFeatures: ImmutableList<DisplayFeature>,
) {
    home(
        singlePaneStartDestination = homeNavigationRoute,
        pane1StartDestination = characterDetailsRoute,
        pane2StartDestination = characterDetailsRoute,
    ) { childNavController ->
        homeNavigation(
            windowSizeClass = windowSizeClass,
            displayFeatures = displayFeatures,
            navigationItems = homeNavigationItems,
        ) {
            characterList(
                onNavigateToSettings = navController::navigateToMainSettings,
                onNavigateToCharacterDetails = {
                    childNavController.navigateToCharacterDetails(
                        Screen.Pane2,
                    )
                },
            )

            episodeList()

            locationList()
        }

        characterDetails()

        episodeDetails()

        locationDetails()
    }
}
