package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.debug.DebugSettings

@Immutable
public data class DebugDrawerUiState(
  val versionName: String,
  val versionCode: Long,
  val packageName: String,
  val debugSettings: ResourceState<DebugSettings> = Uninitialized,
) {
  val isLoading: Boolean
    get() = debugSettings is Incomplete
}
