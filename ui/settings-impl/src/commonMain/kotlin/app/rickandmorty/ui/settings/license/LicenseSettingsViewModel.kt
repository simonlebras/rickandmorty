package app.rickandmorty.ui.settings.license

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.license.LicenseRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class LicenseSettingsViewModel(private val licenseRepository: LicenseRepository) :
  ViewModel() {
  private val licenses = ResourceController(resource = suspend { licenseRepository.getLicenses() })

  public val uiState: StateFlow<LicenseSettingsUiState> =
    licenses.state
      .map { licenses -> LicenseSettingsUiState(licenses = licenses) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LicenseSettingsUiState(),
      )
}
