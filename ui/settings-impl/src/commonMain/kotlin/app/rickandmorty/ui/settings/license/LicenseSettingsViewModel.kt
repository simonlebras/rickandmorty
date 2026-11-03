package app.rickandmorty.ui.settings.license

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.license.License
import app.rickandmorty.data.license.LicenseRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.collections.immutable.ImmutableList

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class LicenseSettingsViewModel(
  private val licenseRepository: LicenseRepository,
) : ViewModel() {

  public val state: State<LicenseSettingsUiState> =
    transform(
      initialValue = LicenseSettingsUiState(),
      scope = viewModelScope,
    ) { previous ->
      val licenses by
        produceState<ImmutableList<License>?>(null) {
          value = licenseRepository.getLicenses()
        }
      LicenseSettingsUiState(licenses = licenses ?: return@transform previous)
    }
}
