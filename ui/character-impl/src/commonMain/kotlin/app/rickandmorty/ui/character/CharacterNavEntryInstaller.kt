package app.rickandmorty.ui.character

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import app.rickandmorty.ui.character.list.CharacterListScreen
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(UiScope::class)
public class CharacterNavEntryInstaller : NavEntryInstaller {
  override fun EntryProviderScope<NavKey>.install() {
    entry<CharacterListNavKey> { CharacterListScreen() }
  }
}
