package app.rickandmorty.core.strictmode.inject

import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.startup.Initializer
import app.rickandmorty.core.strictmode.StrictModeInitializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object StrictModeInitializerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideStrictModeInitializer(): Set<Initializer> =
    if (BuildFlags.isDebug) setOf(StrictModeInitializer()) else emptySet()
}
