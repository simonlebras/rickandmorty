package app.rickandmorty.core.logger.debug.inject

import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.logger.debug.DebugLoggerInitializer
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object DebugLoggerInitializerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideDebugLoggerInitializer(): Set<Initializer> =
    if (BuildFlags.isDebug) setOf(DebugLoggerInitializer()) else emptySet()
}
