package app.rickandmorty.ui.settings.navigation

import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.LocalListDetailSceneScope
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.core.navigation.Navigator
import app.rickandmorty.ui.settings.language.LanguageSettingsScreen
import app.rickandmorty.ui.settings.license.LicenseSettingsScreen
import app.rickandmorty.ui.settings.main.MainSettingsItem
import app.rickandmorty.ui.settings.main.MainSettingsScreen
import dev.zacsweers.metro.ContributesIntoSet

private const val SettingsSceneKey = "settings"

@ContributesIntoSet(UiScope::class)
internal class SettingsNavEntryInstaller(private val navigator: Navigator) : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<MainSettingsNavKey>(
      metadata = ListDetailSceneStrategy.listPane(sceneKey = SettingsSceneKey)
    ) {
      val selectedItem =
        LocalListDetailSceneScope.current?.let {
          when (navigator.currentRoute) {
            is LanguageSettingsNavKey -> MainSettingsItem.Language
            is LicenseSettingsNavKey -> MainSettingsItem.Licenses
            else -> null
          }
        }

      MainSettingsScreen(
        selectedItem = selectedItem,
        onNavigateUp = navigator::goBack,
        onNavigateToLanguageSettings = {
          navigator.navigate(route = LanguageSettingsNavKey, popUpTo = MainSettingsNavKey)
        },
        onNavigateToLicenseSettings = {
          navigator.navigate(route = LicenseSettingsNavKey, popUpTo = MainSettingsNavKey)
        },
      )
    }

    entry<LanguageSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey)
    ) {
      LanguageSettingsScreen(
        onNavigateUp = navigator::goBack,
        showBackButton = LocalListDetailSceneScope.current == null,
      )
    }

    entry<LicenseSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey)
    ) {
      LicenseSettingsScreen(
        onNavigateUp = navigator::goBack,
        showBackButton = LocalListDetailSceneScope.current == null,
      )
    }
  }
}
