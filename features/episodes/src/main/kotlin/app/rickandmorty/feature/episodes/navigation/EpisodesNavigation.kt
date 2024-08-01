package app.rickandmorty.feature.episodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.episodes.list.EpisodeListScreen
import kotlinx.serialization.Serializable

@Serializable
public data object EpisodeList

public fun NavHostController.navigateToEpisodeList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = EpisodeList,
        builder = builder,
    )
}

public fun NavGraphBuilder.episodeList(
    onNavigateToSettings: () -> Unit,
) {
    composable<EpisodeList> {
        EpisodeListScreen(
            onNavigateToSettings = onNavigateToSettings,
        )
    }
}
