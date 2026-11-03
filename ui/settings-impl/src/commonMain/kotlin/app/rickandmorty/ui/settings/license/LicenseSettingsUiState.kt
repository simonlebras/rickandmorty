package app.rickandmorty.ui.settings.license

import androidx.compose.runtime.Stable
import app.rickandmorty.data.license.License
import kotlinx.collections.immutable.ImmutableList

@Stable public data class LicenseSettingsUiState(val licenses: ImmutableList<License>? = null)

public val LicenseSettingsUiState.isLoading: Boolean
  get() = licenses == null
