package app.rickandmorty.settings.dartheme

import androidx.compose.runtime.Immutable
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import app.rickandmorty.theme.domain.Theme

@Immutable
internal data class DarkThemeUiState(
    val theme: ResourceState<Theme> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete
}
