package app.rickandmorty.feature.settings.theme

import androidx.compose.runtime.Immutable
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import app.rickandmorty.theme.domain.NightMode
import app.rickandmorty.theme.domain.Theme
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class ThemeSettingsUiState(
    val theme: ResourceState<Theme> = Uninitialized,
    val availableNightModes: ResourceState<ImmutableList<NightMode>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete ||
            availableNightModes is Incomplete
}
