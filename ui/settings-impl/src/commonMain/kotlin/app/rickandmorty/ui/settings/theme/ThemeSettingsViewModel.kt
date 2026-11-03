package app.rickandmorty.ui.settings.theme

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.theme.NightMode
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class ThemeSettingsViewModel(
  private val themeRepository: ThemeRepository,
) : ViewModel() {

  public val state: State<ThemeSettingsUiState> =
    transform(
      initialValue =
        ThemeSettingsUiState(
          theme = themeRepository.theme.value,
          availableNightModes = themeRepository.getAvailableNightModes(),
        ),
      scope = viewModelScope,
    ) { previous ->
      ThemeSettingsUiState(
        theme = themeRepository.theme.value ?: return@transform previous,
        availableNightModes = themeRepository.getAvailableNightModes(),
      )
    }

  public fun selectNightMode(nightMode: NightMode) {
    themeRepository.setNightMode(nightMode)
  }
}
