package app.rickandmorty.core.coil.logger.inject

import co.touchlab.kermit.Logger as KermitLogger
import co.touchlab.kermit.Severity
import coil3.util.Logger as CoilLogger
import coil3.util.Logger.Level
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

private const val TAG = "Coil"

@ContributesTo(AppScope::class)
public interface CoilLoggerProvider {
  public companion object {
    @Provides public fun provideLogger(): CoilLogger = KermitLogger.asCoilLogger()
  }
}

private fun KermitLogger.asCoilLogger(): CoilLogger =
  object : CoilLogger {
    override var minLevel: Level = Level.Debug

    override fun log(tag: String, level: Level, message: String?, throwable: Throwable?) {
      this@asCoilLogger.log(level.toSeverity(), TAG, throwable, message.orEmpty())
    }
  }

private fun Level.toSeverity(): Severity =
  when (this) {
    Level.Verbose -> Severity.Verbose
    Level.Debug -> Severity.Debug
    Level.Info -> Severity.Info
    Level.Warn -> Severity.Warn
    Level.Error -> Severity.Error
  }
