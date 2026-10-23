package app.rickandmorty.ui

import androidx.compose.runtime.Stable
import app.rickandmorty.data.theme.Theme
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable

@Stable
@Snapshottable
interface MainUiState {
  @SnapshotSpec data class Immutable(val theme: Theme? = null) : MainUiState
}

companion operator fun MainUiState.invoke(): MainUiState.Immutable = MainUiState.Immutable()

val MainUiState.isLoading: Boolean
  get() = theme == null

val MainUiState.useDynamicColor: Boolean
  get() = theme?.useDynamicColor ?: false
