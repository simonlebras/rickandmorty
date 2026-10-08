package app.rickandmorty.core.compose.diagnostic.inject

import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.compose.diagnostic.ComposeDiagnosticInitializer
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object ComposeDiagnosticInitializerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideComposeDiagnosticInitializer(): Set<Initializer> =
    if (!BuildFlags.isDebug) setOf(ComposeDiagnosticInitializer()) else emptySet()
}
