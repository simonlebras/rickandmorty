package app.rickandmorty.core.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey

public fun interface NavEntryInstaller {
  public fun EntryProviderScope<NavKey>.install()
}
