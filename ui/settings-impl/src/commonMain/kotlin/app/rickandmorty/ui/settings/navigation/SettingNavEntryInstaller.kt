package app.rickandmorty.ui.settings.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.LocalListDetailSceneScope
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
import app.rickandmorty.ui.settings.license.LicenseSettingsScreen
import app.rickandmorty.ui.settings.main.MainSettingsItem
import app.rickandmorty.ui.settings.main.MainSettingsScreen
import app.rickandmorty.ui.settings.theme.ThemeSettingsDialog
import dev.zacsweers.metro.ContributesIntoSet

private const val SettingsSceneKey = "settings"

@ContributesIntoSet(UiScope::class)
internal class SettingsNavEntryInstaller : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<MainSettingsNavKey>(
      metadata = ListDetailSceneStrategy.listPane(sceneKey = SettingsSceneKey)
    ) {
      val navigator = LocalNavigator.current

      val selectedItem =
        LocalListDetailSceneScope.current?.let {
          when (navigator.currentRoute) {
            is LanguageSettingsNavKey -> MainSettingsItem.Language
            is LicenseSettingsNavKey -> MainSettingsItem.Licenses
            else -> null
          }
        }

      var showSettingsDialog by rememberSaveable { mutableStateOf(false) }

      MainSettingsScreen(
        selectedItem = selectedItem,
        onNavigateUp = navigator::goBack,
        onNavigateToThemeSettings = { showSettingsDialog = true },
        onNavigateToLanguageSettings = { navigator.navigate(LanguageSettingsNavKey) },
        onNavigateToLicenseSettings = { navigator.navigate(LicenseSettingsNavKey) },
      )

      if (showSettingsDialog) {
        ThemeSettingsDialog(onDismiss = { showSettingsDialog = false })
      }
    }

    entry<LanguageSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey)
    ) {
      val navigator = LocalNavigator.current

      val showBackButton = LocalListDetailSceneScope.current == null

      LanguageSettingsScreen(onNavigateUp = navigator::goBack, showBackButton = showBackButton)
    }

    entry<LicenseSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey)
    ) {
      val navigator = LocalNavigator.current

      val showBackButton = LocalListDetailSceneScope.current == null

      LicenseSettingsScreen(onNavigateUp = navigator::goBack, showBackButton = showBackButton)
    }
  }
}
