package app.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import app.rickandmorty.characters.characters
import app.rickandmorty.characters.charactersRoute
import app.rickandmorty.episodes.episodes
import app.rickandmorty.locations.locations

@Composable
fun RamNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = charactersRoute,
        modifier = modifier,
    ) {
        characters()

        episodes()

        locations()
    }
}
