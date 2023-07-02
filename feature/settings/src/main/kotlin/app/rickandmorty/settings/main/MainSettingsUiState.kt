package app.rickandmorty.settings.main

import androidx.compose.runtime.Immutable
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized

@Immutable
internal data class MainSettingsUiState(
    val applicationLocale: ResourceState<Locale?> = Uninitialized,
) {
    val isLoading: Boolean
        get() = applicationLocale is Incomplete
}
