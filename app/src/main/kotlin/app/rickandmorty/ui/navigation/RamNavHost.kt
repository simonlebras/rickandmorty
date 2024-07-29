package app.rickandmorty.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.rickandmorty.feature.characters.navigation.characterList
import app.rickandmorty.feature.characters.navigation.characterListRoute
import app.rickandmorty.feature.episodes.navigation.episodeList
import app.rickandmorty.feature.locations.navigation.locationList

@Composable
fun RamNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = characterListRoute,
        modifier = modifier,
    ) {
        characterList(
            onNavigateToSettings = navController::navigateToMainSettings,
            onNavigateToCharacterDetails = {},
        )

        episodeList(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

        locationList(
            onNavigateToSettings = navController::navigateToMainSettings,
        )

        settings(
            navController = navController,
            onNavigateUp = navController::navigateUp,
        )
    }
}
