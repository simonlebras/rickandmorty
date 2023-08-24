package app.rickandmorty.feature.settings.main

import androidx.compose.runtime.Immutable
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import app.rickandmorty.theme.domain.Theme

@Immutable
internal data class MainSettingsUiState(
    val theme: ResourceState<Theme?> = Uninitialized,
    val applicationLocale: ResourceState<Locale?> = Uninitialized,
) {
    val isLoading: Boolean
        get() = theme is Incomplete ||
            applicationLocale is Incomplete
}