package app.rickandmorty.inject

import android.app.Activity
import android.content.Context
import androidx.savedstate.serialization.SavedStateConfiguration
import app.rickandmorty.core.metro.ActivityContext
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.EntryProvider
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

@GraphExtension(UiScope::class)
interface UiGraph : ViewModelGraph {
  val viewModelFactory: MetroViewModelFactory

  val entryProvider: EntryProvider

  val savedStateConfiguration: SavedStateConfiguration

  @Binds @ActivityContext fun bindActivity(activity: Activity): Context

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  fun interface Factory {
    fun create(@Provides activity: Activity): UiGraph
  }
}
