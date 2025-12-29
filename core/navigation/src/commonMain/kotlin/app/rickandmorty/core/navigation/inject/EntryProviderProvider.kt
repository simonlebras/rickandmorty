package app.rickandmorty.core.navigation.inject

import androidx.navigation3.runtime.entryProvider
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.EntryProvider
import app.rickandmorty.core.navigation.NavEntryInstaller
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides

@ContributesTo(UiScope::class)
public interface EntryProviderProvider {
  @Multibinds(allowEmpty = true) public val navEntryInstallers: Set<NavEntryInstaller>

  public companion object {
    @Provides
    public fun provideEntryProvider(navEntryInstallers: Set<NavEntryInstaller>): EntryProvider {
      return entryProvider { navEntryInstallers.forEach { with(it) { install() } } }
    }
  }
}
