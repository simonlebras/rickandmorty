package app.rickandmorty.core.logger.debug

import app.rickandmorty.core.startup.Initializer
import co.touchlab.kermit.Logger
import co.touchlab.kermit.Severity
import co.touchlab.kermit.platformLogWriter
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(scope = AppScope::class, multibinding = true)
public class DebugLoggerInitializer : Initializer {
    override fun initialize() {
        with(Logger) {
            setMinSeverity(Severity.Debug)
            addLogWriter(platformLogWriter())
        }
    }
}
