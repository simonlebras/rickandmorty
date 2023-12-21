package app.rickandmorty.feature.settings.language

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.locale.domain.Locale
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
