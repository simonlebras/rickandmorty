package app.rickandmorty.settings.language

import androidx.compose.runtime.Immutable
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class LanguageSettingsUiState(
    val applicationLocale: ResourceState<Locale?> = Uninitialized,
    val availableLocales: ResourceState<ImmutableList<Locale>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = applicationLocale is Incomplete ||
            availableLocales is Incomplete
}
