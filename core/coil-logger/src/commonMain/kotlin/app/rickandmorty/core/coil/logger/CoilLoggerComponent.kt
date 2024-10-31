package app.rickandmorty.core.coil.logger

import co.touchlab.kermit.Logger as KermitLogger
import co.touchlab.kermit.Severity
import coil3.util.Logger as CoilLogger
import coil3.util.Logger.Level
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

private const val TAG = "Coil"

@ContributesTo(AppScope::class)
public interface CoilLoggerComponent {
    @Provides
    public fun provideLogger(): CoilLogger = KermitLogger.asCoilLogger()
}

private fun KermitLogger.asCoilLogger(): CoilLogger = object : CoilLogger {
    override var minLevel: Level = Level.Debug

    override fun log(tag: String, level: Level, message: String?, throwable: Throwable?) {
        this@asCoilLogger.log(level.toSeverity(), TAG, throwable, message.orEmpty())
    }
}

private fun Level.toSeverity(): Severity = when (this) {
    Level.Verbose -> Severity.Verbose
    Level.Debug -> Severity.Debug
    Level.Info -> Severity.Info
    Level.Warn -> Severity.Warn
    Level.Error -> Severity.Error
}
