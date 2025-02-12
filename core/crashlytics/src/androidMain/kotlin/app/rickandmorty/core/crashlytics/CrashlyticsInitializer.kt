package app.rickandmorty.core.crashlytics

import app.rickandmorty.core.startup.Initializer
import co.touchlab.crashkios.crashlytics.enableCrashlytics
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(scope = AppScope::class, multibinding = true)
public class CrashlyticsInitializer : Initializer {
    override fun initialize() {
        enableCrashlytics()
    }
}
