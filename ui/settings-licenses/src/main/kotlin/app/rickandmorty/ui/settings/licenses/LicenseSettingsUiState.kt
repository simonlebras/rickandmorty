package app.rickandmorty.ui.settings.licenses

import app.rickandmorty.core.resourcestate.ResourceState
import app.rickandmorty.core.resourcestate.Uninitialized
import app.rickandmorty.data.model.License
import kotlinx.collections.immutable.ImmutableList

public data class LicenseSettingsUiState(
    val licenses: ResourceState<ImmutableList<License>> = Uninitialized,
)
