package app.rickandmorty.core.ui

import app.rickandmorty.core.startup.Initializer
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeFeatureFlags
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
internal class HazeInitializer : Initializer {
  @OptIn(ExperimentalHazeApi::class)
  override fun initialize() {
    HazeFeatureFlags.isPlatformBackdropEnabled = true
  }
}
