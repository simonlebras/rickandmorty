package app.rickandmorty.core.compose.debug

import androidx.compose.runtime.Composer
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoSet

@ContributesIntoSet(AppScope::class)
public class ComposeDiagnosticInitializer : Initializer {
  override fun initialize() {
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.SourceInformation)
  }
}
