package app.rickandmorty.settings.appearance

import androidx.compose.runtime.Immutable
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import app.rickandmorty.theme.domain.NightMode
import app.rickandmorty.theme.domain.Theme
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class AppearanceSettingsUiState(
    val theme: ResourceState<Theme> = Uninitialized,
    val availableNightModes: ResourceState<ImmutableList<NightMode>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete ||
            availableNightModes is Incomplete
}
