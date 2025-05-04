package app.rickandmorty.core.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
public class CrashlyticsInitializer : Initializer {
  override fun initialize() {
    enableCrashlytics()
    setCrashlyticsUnhandledExceptionHook()
  }
}
