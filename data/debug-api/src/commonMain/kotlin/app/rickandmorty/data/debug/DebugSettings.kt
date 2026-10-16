package app.rickandmorty.data.debug

import androidx.compose.runtime.Immutable

@Immutable
public data class DebugSettings(
  val keylineOverlayEnabled: Boolean,
  val lookaheadDebuggingEnabled: Boolean,
)
