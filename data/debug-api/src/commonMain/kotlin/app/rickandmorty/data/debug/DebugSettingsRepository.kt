package app.rickandmorty.data.debug

import androidx.compose.runtime.State

public interface DebugSettingsRepository {
  public val debugSettings: State<DebugSettings?>

  public fun setKeylineOverlayEnabled(enabled: Boolean)

  public fun setLookaheadDebuggingEnabled(enabled: Boolean)
}
