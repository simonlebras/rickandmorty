package app.rickandmorty.ui.character.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.LocalNavigator
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.ui.character.CharacterListNavKey
import app.rickandmorty.ui.character.list.CharacterListScreen
import app.rickandmorty.ui.settings.MainSettingsNavKey
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
public class CharacterNavEntryInstaller : NavEntryInstaller {
  override fun EntryProviderScope<NavKey>.install() {
    entry<CharacterListNavKey> {
      val navigator = LocalNavigator.current
      CharacterListScreen(onNavigateToSettings = { navigator.navigate(MainSettingsNavKey) })
    }
  }
}
