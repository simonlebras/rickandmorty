package app.rickandmorty.data.debug

import kotlinx.coroutines.flow.Flow

public interface DebugSettingsRepository {
  public fun getDebugSettings(): Flow<DebugSettings>

  public suspend fun setKeylineOverlayEnabled(enabled: Boolean)

  public suspend fun setLookaheadDebuggingEnabled(enabled: Boolean)
}
