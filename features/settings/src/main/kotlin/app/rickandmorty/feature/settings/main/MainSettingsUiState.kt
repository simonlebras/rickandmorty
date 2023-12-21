package app.rickandmorty.feature.settings.main

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.Theme
import app.rickandmorty.locale.domain.Locale

@Immutable
internal data class MainSettingsUiState(
    val theme: ResourceState<Theme?> = Uninitialized,
    val applicationLocale: ResourceState<Locale?> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete ||
            applicationLocale is Incomplete
}
