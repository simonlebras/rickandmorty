package app.rickandmorty.ui.settings.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.LocalListDetailSceneScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.core.navigation.Navigator
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.ui.settings.language.LanguageSettingsScreen
import app.rickandmorty.ui.settings.license.LicenseSettingsScreen
import app.rickandmorty.ui.settings.main.MainSettingsItem
import app.rickandmorty.ui.settings.main.MainSettingsScreen
import dev.zacsweers.metro.ContributesIntoSet
import org.jetbrains.compose.resources.painterResource

private const val SettingsSceneKey = "settings"

@ContributesIntoSet(UiScope::class)
internal class SettingsNavEntryInstaller(private val navigator: Navigator) : NavEntryInstaller {
  @OptIn(ExperimentalMaterial3AdaptiveApi::class)
  override fun EntryProviderScope<NavKey>.install() {
    entry<MainSettingsNavKey>(
      metadata =
        ListDetailSceneStrategy.listPane(
          sceneKey = SettingsSceneKey,
          detailPlaceholder = {
            HazeScaffold { contentPadding ->
              Box(
                modifier = Modifier.fillMaxSize().padding(contentPadding),
                contentAlignment = Alignment.Center,
              ) {
                Icon(
                  painter = painterResource(RamIcons.Filled.Settings),
                  contentDescription = null,
                  modifier = Modifier.size(64.dp),
                )
              }
            }
          },
        ),
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
        onNavigateUp = dropUnlessResumed(block = navigator::goBack),
        onNavigateToLanguageSettings =
          dropUnlessResumed {
            navigator.navigate(route = LanguageSettingsNavKey, popUpTo = MainSettingsNavKey)
          },
        onNavigateToLicenseSettings =
          dropUnlessResumed {
            navigator.navigate(route = LicenseSettingsNavKey, popUpTo = MainSettingsNavKey)
          },
      )
    }

    entry<LanguageSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey),
    ) {
      LanguageSettingsScreen(
        onNavigateUp = dropUnlessResumed(block = navigator::goBack),
        showBackButton = LocalListDetailSceneScope.current == null,
      )
    }

    entry<LicenseSettingsNavKey>(
      metadata = ListDetailSceneStrategy.detailPane(sceneKey = SettingsSceneKey),
    ) {
      LicenseSettingsScreen(
        onNavigateUp = dropUnlessResumed(block = navigator::goBack),
        showBackButton = LocalListDetailSceneScope.current == null,
      )
    }
  }
}
