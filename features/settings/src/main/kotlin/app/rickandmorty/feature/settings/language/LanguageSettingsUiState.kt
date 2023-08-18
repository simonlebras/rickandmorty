package app.rickandmorty.feature.settings.language

import androidx.compose.runtime.Immutable
import app.rickandmorty.resourcestate.Incomplete
import app.rickandmorty.resourcestate.ResourceState
import app.rickandmorty.resourcestate.Uninitialized
import app.rickandmorty.service.locale.model.Locale
import kotlinx.collections.immutable.ImmutableList

@Immutable
internal data class LanguageSettingsUiState(
    val applicationLocale: ResourceState<Locale?> = Uninitialized,
    val availableApplicationLocales: ResourceState<ImmutableList<Locale>> = Uninitialized,
) {
    val isLoading: Boolean
        get() = applicationLocale is Incomplete ||
            availableApplicationLocales is Incomplete
}
