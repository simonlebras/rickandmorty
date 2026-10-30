package app.rickandmorty.ui.episode.navigation

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.metadata
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.core.navigation.Navigator
import app.rickandmorty.core.ui.navigationSuite
import app.rickandmorty.ui.episode.list.EpisodeListScreen
import app.rickandmorty.ui.settings.navigation.MainSettingsNavKey
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
internal class EpisodeNavEntryInstaller(private val navigator: Navigator) : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<EpisodeListNavKey>(
      metadata = metadata { navigationSuite() } + ListDetailSceneStrategy.listPane(),
    ) {
      val listState = rememberLazyListState()

      LaunchedEffect(Unit) {
        navigator.reselectEvents(EpisodeListNavKey).collect {
          listState.animateScrollToItem(0)
        }
      }

      EpisodeListScreen(
        listState = listState,
        onNavigateToSettings = { navigator.navigate(MainSettingsNavKey) },
      )
    }
  }
}
