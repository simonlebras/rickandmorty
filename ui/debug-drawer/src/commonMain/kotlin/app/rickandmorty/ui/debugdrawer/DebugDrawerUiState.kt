package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Stable
import app.rickandmorty.data.debug.DebugSettings
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable

@Stable
@Snapshottable
public interface DebugDrawerUiState {
  @SnapshotSpec
  public data class Immutable(
    val versionName: String,
    val versionCode: Long,
    val packageName: String,
    val debugSettings: DebugSettings? = null,
  ) : DebugDrawerUiState
}

public companion operator fun DebugDrawerUiState.invoke(
  versionName: String,
  versionCode: Long,
  packageName: String,
): DebugDrawerUiState.Immutable =
  DebugDrawerUiState.Immutable(
    versionName = versionName,
    versionCode = versionCode,
    packageName = packageName,
  )

public sealed class DebugDrawerAction(public val key: String) {
  public data class SetKeylineOverlayEnabled(val enabled: Boolean) :
    DebugDrawerAction(key = "SetKeylineOverlayEnabled")

  public data class SetLookaheadDebuggingEnabled(val enabled: Boolean) :
    DebugDrawerAction(key = "SetLookaheadDebuggingEnabled")

  public data object ClearImageCache : DebugDrawerAction(key = "ClearImageCache")
}
