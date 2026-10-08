package app.rickandmorty.core.coil.logger.inject

import app.rickandmorty.core.base.BuildFlags
import co.touchlab.kermit.Logger
import co.touchlab.kermit.coil.KermitCoilLogger
import coil3.util.Logger as CoilLogger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object CoilLoggerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideCoilLogger(): Set<CoilLogger> =
    if (BuildFlags.isDebug) setOf(KermitCoilLogger(Logger.withTag("Coil"))) else emptySet()
}
