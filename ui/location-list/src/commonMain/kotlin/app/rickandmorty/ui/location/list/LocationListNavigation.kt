package app.rickandmorty.ui.location.list

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import dev.zacsweers.metro.ContributesIntoSet
import kotlinx.serialization.Serializable

@Serializable public data object LocationList : NavKey

@ContributesIntoSet(UiScope::class)
public class LocationListNavEntryInstaller : NavEntryInstaller {
  override fun EntryProviderScope<NavKey>.install() {
    entry<LocationList> { LocationListScreen() }
  }
}
