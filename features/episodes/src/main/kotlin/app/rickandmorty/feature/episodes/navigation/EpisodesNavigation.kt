package app.rickandmorty.feature.episodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.episodes.list.EpisodeListScreen

public const val episodeListRoute: String = "episodeList"
private const val episodeDetailsRoute = "episodeDetails"

public fun NavHostController.navigateToEpisodeList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = episodeListRoute,
        builder = builder,
    )
}

public fun NavGraphBuilder.episodeList(
    onNavigateToSettings: () -> Unit,
) {
    composable(route = episodeListRoute) {
        EpisodeListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
