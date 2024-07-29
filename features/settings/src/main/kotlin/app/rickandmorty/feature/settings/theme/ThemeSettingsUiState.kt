package app.rickandmorty.feature.settings.theme

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.data.model.Theme
import kotlinx.collections.immutable.ImmutableList

@Immutable
public data class ThemeSettingsUiState(
    val theme: ResourceState<Theme> = Uninitialized,
    val availableNightModes: ResourceState<ImmutableList<NightMode>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete ||
            availableNightModes is Incomplete
}
