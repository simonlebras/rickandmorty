package app.rickandmorty.core.logger.debug

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter

internal class DebugLoggerInitializer : Initializer {
  override fun initialize() {
    with(Logger) {
      setMinSeverity(Severity.Debug)
      setLogWriters(platformLogWriter())
    }
  }
}
