package app.rickandmorty.ui.episode.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.ui.episode.list.EpisodeListScreen
import app.rickandmorty.ui.settings.navigation.MainSettingsNavKey
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
public class EpisodeNavEntryInstaller : NavEntryInstaller {
  override fun EntryProviderScope<NavKey>.install() {
    entry<EpisodeListNavKey> {
      val navigator = LocalNavigator.current
      EpisodeListScreen(onNavigateToSettings = { navigator.navigate(MainSettingsNavKey) })
    }
  }
}
