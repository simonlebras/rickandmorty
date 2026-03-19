package app.rickandmorty.ui.settings.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.ui.settings.language.LanguageSettingsScreen
import app.rickandmorty.ui.settings.main.MainSettingsScreen
import app.rickandmorty.ui.settings.theme.ThemeSettingsDialog
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
public class SettingsNavEntryInstaller : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<MainSettingsNavKey>(metadata = ListDetailSceneStrategy.listPane()) {
      var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

      val navigator = LocalNavigator.current
      MainSettingsScreen(
        onNavigateUp = navigator::goBack,
        onNavigateToThemeSettings = { showSettingsDialog = true },
        onNavigateToLanguageSettings = { navigator.navigate(LanguageSettingsNavKey) },
        onNavigateToOssLicenses = {},
      )

      if (showSettingsDialog) {
        ThemeSettingsDialog(onDismiss = { showSettingsDialog = false })
      }
    }

    entry<LanguageSettingsNavKey> {
      val navigator = LocalNavigator.current
      LanguageSettingsScreen(onNavigateUp = navigator::goBack)
    }
  }
}
