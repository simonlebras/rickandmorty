package app.rickandmorty.core.compose.debug

import androidx.compose.runtime.Composer
import androidx.compose.runtime.ExperimentalComposeRuntimeApi
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject

@Inject
@ContributesIntoSet(AppScope::class)
public class ComposeDiagnosticInitializer : Initializer {
  @OptIn(ExperimentalComposeRuntimeApi::class)
  override fun initialize() {
    Composer.setDiagnosticStackTraceEnabled(true)
  }
}
