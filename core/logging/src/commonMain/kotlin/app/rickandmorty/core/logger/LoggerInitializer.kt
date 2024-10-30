package app.rickandmorty.core.logger

import app.rickandmorty.core.startup.Initializer
import me.tatarka.inject.annotations.Inject
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class, multibinding = true)
public class LoggerInitializer : Initializer {
    override fun initialize() {
    }
}
