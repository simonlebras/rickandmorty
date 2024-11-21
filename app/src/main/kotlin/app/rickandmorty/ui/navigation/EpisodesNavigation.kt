package app.rickandmorty.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.ui.episode.list.EpisodeListScreen
import kotlinx.serialization.Serializable

@Serializable
data object EpisodeList

fun NavHostController.navigateToEpisodeList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = EpisodeList,
        builder = builder,
    )
}

fun NavGraphBuilder.episodeList(
    onNavigateToSettings: () -> Unit,
) {
    composable<EpisodeList> {
        EpisodeListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
