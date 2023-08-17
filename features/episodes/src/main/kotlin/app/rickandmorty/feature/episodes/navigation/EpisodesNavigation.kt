package app.rickandmorty.feature.episodes.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import app.rickandmorty.feature.episodes.details.EpisodeDetailsScreen
import app.rickandmorty.feature.episodes.list.EpisodeListScreen
import com.microsoft.device.dualscreen.twopanelayout.Screen
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneNavScope
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.composable as twopanelayoutComposable

public const val episodeListRoute: String = "episodeList"
private const val episodeDetailsRoute = "episodeDetails"

public fun NavHostController.navigateToEpisodeList(builder: NavOptionsBuilder.() -> Unit = {}) {
    navigate(
        route = episodeListRoute,
        builder = builder,
    )
}

context(TwoPaneNavScope)
public fun NavHostController.navigateToEpisodeDetails(
    launchScreen: Screen,
    builder: NavOptionsBuilder.() -> Unit = {},
) {
    navigateTo(
        route = episodeDetailsRoute,
        launchScreen = launchScreen,
        builder = builder,
    )
}

public fun NavGraphBuilder.episodeList() {
    composable(route = episodeListRoute) {
        EpisodeListScreen()
    }
}

public fun NavGraphBuilder.episodeDetails() {
    twopanelayoutComposable(route = episodeDetailsRoute) {
        EpisodeDetailsScreen()
    }
}
