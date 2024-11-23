package app.rickandmorty.ui.settings.language

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.Locale
import kotlinx.collections.immutable.ImmutableList

@Immutable
public data class LanguageSettingsUiState(
    val appLocale: ResourceState<Locale?> = Uninitialized,
    val availableAppLocales: ResourceState<ImmutableList<Locale>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = appLocale is Incomplete ||
            availableAppLocales is Incomplete
}
