package app.rickandmorty.core.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import co.touchlab.crashkios.crashlytics.setCrashlyticsUnhandledExceptionHook

internal class CrashlyticsInitializer : Initializer {
  override fun initialize() {
    enableCrashlytics()
    setCrashlyticsUnhandledExceptionHook()
  }
}
