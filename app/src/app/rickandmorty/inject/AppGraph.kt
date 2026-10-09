package app.rickandmorty.inject

import android.app.Application
import android.content.Context
import app.rickandmorty.RamApplication
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Binds
import dev.zacsweers.metro.DependencyGraph
import dev.zacsweers.metro.Provides
import dev.zacsweers.metrox.android.MetroAppComponentProviders

@DependencyGraph(AppScope::class)
interface AppGraph : MetroAppComponentProviders {
  fun inject(application: RamApplication)

  @Binds @AppContext fun bindApplication(application: Application): Context

  @DependencyGraph.Factory
  fun interface Factory {
    fun create(@Provides application: Application): AppGraph
  }
}
