package app.rickandmorty.core.logger.debug

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
public class DebugLoggerInitializer : Initializer {
  override fun initialize() {
    with(Logger) {
      setMinSeverity(Severity.Debug)
      addLogWriter(platformLogWriter())
    }
  }
}
