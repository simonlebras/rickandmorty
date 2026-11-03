package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Stable
import app.rickandmorty.data.debug.DebugSettings

@Stable
public data class DebugDrawerUiState(
  val versionName: String,
  val versionCode: Long,
  val packageName: String,
  val debugSettings: DebugSettings? = null,
)
