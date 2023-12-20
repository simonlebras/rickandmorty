package app.rickandmorty

import androidx.compose.runtime.Immutable
import app.rickandmorty.data.model.Theme
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized

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
