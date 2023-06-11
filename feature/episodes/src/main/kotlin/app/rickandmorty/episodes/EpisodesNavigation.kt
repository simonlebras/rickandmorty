package app.rickandmorty.episodes

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable

public const val episodesRoute: String = "episodes"

public fun NavController.navigateToEpisodes(navOptions: NavOptions? = null) {
    navigate(episodesRoute, navOptions)
}

public fun NavGraphBuilder.episodes() {
    composable(route = episodesRoute) {
        EpisodesScreen()
    }
}
