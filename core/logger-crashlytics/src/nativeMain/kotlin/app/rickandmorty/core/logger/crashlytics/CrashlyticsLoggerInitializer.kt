package app.rickandmorty.core.logger.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.ExperimentalKermitApi
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.crashlytics.CrashlyticsLogWriter
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@OptIn(ExperimentalKermitApi::class)
@Inject
@ContributesBinding(AppScope::class, multibinding = true)
public class CrashlyticsLoggerInitializer : Initializer {
    override fun initialize() {
        with(Logger) {
            setMinSeverity(Severity.Error)
            addLogWriter(CrashlyticsLogWriter())
        }
    }
}
