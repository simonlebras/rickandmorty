package app.rickandmorty.data.debug

import androidx.compose.runtime.Immutable
import kotlinx.serialization.Serializable

@Serializable
@Immutable
public data class DebugSettings(
  val keylineOverlayEnabled: Boolean,
  val lookaheadDebuggingEnabled: Boolean,
)
