package app.rickandmorty.core.logger.crashlytics.inject

import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.logger.crashlytics.CrashlyticsLoggerInitializer
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object CrashlyticsLoggerInitializerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideCrashlyticsLoggerInitializer(): Set<Initializer> =
    if (!BuildFlags.isDebug) setOf(CrashlyticsLoggerInitializer()) else emptySet()
}
