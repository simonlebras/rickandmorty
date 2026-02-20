package app.rickandmorty.ui.location.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.core.ui.listPaneWithNavSuite
import app.rickandmorty.ui.location.list.LocationListScreen
import app.rickandmorty.ui.settings.navigation.MainSettingsNavKey
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
public class LocationNavEntryInstaller : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<LocationListNavKey>(metadata = listPaneWithNavSuite()) {
      val navigator = LocalNavigator.current
      LocationListScreen(onNavigateToSettings = { navigator.navigate(MainSettingsNavKey) })
    }
  }
}
