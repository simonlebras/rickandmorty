package app.rickandmorty.core.compose.diagnostic

import androidx.compose.runtime.Composer
import androidx.compose.runtime.tooling.ComposeStackTraceMode
import app.rickandmorty.core.startup.Initializer

internal class ComposeDiagnosticInitializer : Initializer {
  override fun initialize() {
    Composer.setDiagnosticStackTraceMode(ComposeStackTraceMode.Auto)
  }
}
