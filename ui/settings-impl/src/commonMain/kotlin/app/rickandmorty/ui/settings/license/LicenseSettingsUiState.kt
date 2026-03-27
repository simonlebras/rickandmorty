package app.rickandmorty.ui.settings.license

import app.rickandmorty.core.resourcestate.Incomplete
import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.license.License
import kotlinx.collections.immutable.ImmutableList

public data class LicenseSettingsUiState(
  val licenses: ResourceState<ImmutableList<License>> = Uninitialized
) {
  val isLoading: Boolean
    get() = licenses is Incomplete
}
