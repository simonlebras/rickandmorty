package app.rickandmorty.inject

import android.app.Activity
import android.content.Context
import app.rickandmorty.core.metro.ActivityContext
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.NavEntryInstaller
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Multibinds
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.viewmodel.MetroViewModelFactory
import dev.zacsweers.metrox.viewmodel.ViewModelGraph

@GraphExtension(UiScope::class)
interface UiGraph : ViewModelGraph {
  val viewModelFactory: MetroViewModelFactory

  @Multibinds(allowEmpty = true) val navEntryInstallers: Set<NavEntryInstaller>

  @Binds @ActivityContext fun bindActivity(activity: Activity): Context

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  fun interface Factory {
    fun create(@Provides activity: Activity): UiGraph
  }
}
