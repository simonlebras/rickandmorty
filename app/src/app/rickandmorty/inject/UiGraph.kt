package app.rickandmorty.inject

import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.EntryProvider
import app.rickandmorty.core.navigation.NavigationState
import app.rickandmorty.core.navigation.Navigator
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

/**
 * Retained across configuration changes by `UiGraphHolder`, so nothing bound here may reference the
 * platform host (on Android, the Activity or its Context) — it would outlive it.
 */
@GraphExtension(UiScope::class)
interface UiGraph : ViewModelGraph {
  val entryProvider: EntryProvider
  val navigationState: NavigationState
  val navigator: Navigator

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  fun interface Factory {
    fun create(): UiGraph
  }
}
