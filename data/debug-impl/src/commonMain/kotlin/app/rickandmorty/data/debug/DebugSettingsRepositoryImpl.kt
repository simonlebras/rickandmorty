package app.rickandmorty.data.debug

import androidx.appstate.statestore.StateStore
import androidx.appstate.statestore.StateStoreKey
import androidx.compose.runtime.State
import app.rickandmorty.core.appstate.PersistToDataStoreCompat
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.serialization.Serializable

private val defaultDebugSettings =
  DebugSettings(keylineOverlayEnabled = false, lookaheadDebuggingEnabled = false)

@Serializable
private data object DebugSettingsKey :
  StateStoreKey<DebugSettings?>(defaultValue = null), PersistToDataStoreCompat

@ContributesBinding(AppScope::class)
internal class DebugSettingsRepositoryImpl(
  private val stateStore: StateStore,
) : DebugSettingsRepository {

  override val debugSettings: State<DebugSettings?> = stateStore.getState(DebugSettingsKey)

  override fun setKeylineOverlayEnabled(enabled: Boolean) {
    stateStore.updateState(DebugSettingsKey) { current ->
      (current ?: defaultDebugSettings).copy(keylineOverlayEnabled = enabled)
    }
  }

  override fun setLookaheadDebuggingEnabled(enabled: Boolean) {
    stateStore.updateState(DebugSettingsKey) { current ->
      (current ?: defaultDebugSettings).copy(lookaheadDebuggingEnabled = enabled)
    }
  }
}
