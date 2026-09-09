package app.rickandmorty.core.coil.logger.inject

import co.touchlab.kermit.Logger
import co.touchlab.kermit.coil.KermitCoilLogger
import coil3.util.Logger as CoilLogger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object CoilLoggerProvider {
  @Provides public fun provideCoilLogger(): CoilLogger = KermitCoilLogger(Logger.withTag("Coil"))
}
