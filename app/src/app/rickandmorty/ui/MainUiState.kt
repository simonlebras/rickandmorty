package app.rickandmorty.ui

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.theme.Theme

@Immutable
data class MainUiState(val theme: ResourceState<Theme> = Uninitialized) {
  val isLoading: Boolean
    get() = theme is Incomplete

  val useDynamicColor: Boolean
    get() = theme()?.useDynamicColor ?: false
}
