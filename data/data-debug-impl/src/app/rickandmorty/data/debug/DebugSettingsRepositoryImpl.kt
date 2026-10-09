package app.rickandmorty.data.debug

import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@ContributesBinding(AppScope::class)
internal class DebugSettingsRepositoryImpl(
  private val dataStore: DataStore<DebugSettingsProto>,
  @ApplicationScope private val applicationScope: CoroutineScope,
) : DebugSettingsRepository {
  override fun getDebugSettings(): Flow<DebugSettings> =
    dataStore.data
      .map { debugSettings ->
        DebugSettings(
          keylineOverlayEnabled = debugSettings.keylineOverlayEnabled,
          lookaheadDebuggingEnabled = debugSettings.lookaheadDebuggingEnabled,
        )
      }
      .distinctUntilChanged()

  override suspend fun setKeylineOverlayEnabled(enabled: Boolean) {
    applicationScope
      .launch {
        dataStore.updateData { debug -> debug.copy(keylineOverlayEnabled = enabled) }
      }
      .join()
  }

  override suspend fun setLookaheadDebuggingEnabled(enabled: Boolean) {
    applicationScope
      .launch {
        dataStore.updateData { debug ->
          debug.copy(lookaheadDebuggingEnabled = enabled)
        }
      }
      .join()
  }
}
