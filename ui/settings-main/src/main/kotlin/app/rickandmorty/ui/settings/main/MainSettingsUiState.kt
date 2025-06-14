package app.rickandmorty.ui.settings.main

import androidx.compose.runtime.Immutable
import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.Locale
import app.rickandmorty.data.theme.Theme

@Immutable
public data class MainSettingsUiState(
  val theme: ResourceState<Theme?> = Uninitialized,
  val appLocale: ResourceState<Locale?> = Uninitialized,
) {
  val isLoading: Boolean
    get() = theme is Incomplete || appLocale is Incomplete
}
