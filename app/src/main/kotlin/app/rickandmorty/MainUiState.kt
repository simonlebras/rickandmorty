package app.rickandmorty

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.Theme

@Immutable
data class MainUiState(
    val theme: ResourceState<Theme> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete

    fun useDynamicColor() = when {
        isLoading -> false
        else -> theme()!!.useDynamicColor
    }
}
