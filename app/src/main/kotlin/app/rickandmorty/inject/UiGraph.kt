package app.rickandmorty.inject

import androidx.savedstate.serialization.SavedStateConfiguration
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.EntryProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

@GraphExtension(UiScope::class)
interface UiGraph : ViewModelGraph {
  val viewModelFactory: MetroViewModelFactory

  val entryProvider: EntryProvider

  val savedStateConfiguration: SavedStateConfiguration

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  fun interface Factory {
    fun create(): UiGraph
  }
}
