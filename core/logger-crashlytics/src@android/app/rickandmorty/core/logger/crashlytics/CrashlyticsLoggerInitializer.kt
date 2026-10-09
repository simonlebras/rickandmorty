package app.rickandmorty.core.logger.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter

@OptIn(ExperimentalKermitApi::class)
internal class CrashlyticsLoggerInitializer : Initializer {
  override fun initialize() {
    with(Logger) {
      setMinSeverity(Severity.Error)
      setLogWriters(CrashlyticsLogWriter())
    }
  }
}
