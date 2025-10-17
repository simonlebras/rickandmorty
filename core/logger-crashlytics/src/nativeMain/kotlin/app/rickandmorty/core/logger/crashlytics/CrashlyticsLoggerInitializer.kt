package app.rickandmorty.core.logger.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@OptIn(ExperimentalKermitApi::class)
@ContributesIntoSet(AppScope::class)
public class CrashlyticsLoggerInitializer : Initializer {
  override fun initialize() {
    with(Logger) {
      setMinSeverity(Severity.Error)
      addLogWriter(CrashlyticsLogWriter())
    }
  }
}
