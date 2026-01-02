package app.rickandmorty.ui.settings.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.theme.NightMode
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(UiScope::class)
@ViewModelKey(ThemeSettingsViewModel::class)
public class ThemeSettingsViewModel(private val themeRepository: ThemeRepository) : ViewModel() {
  private val theme = ResourceController(resource = themeRepository.getTheme())

  private val availableNightModes =
    ResourceController(resource = suspend { themeRepository.getAvailableNightModes() })

  public val uiState: StateFlow<ThemeSettingsUiState> =
    combine(theme.state, availableNightModes.state) { theme, availableNightModes ->
        ThemeSettingsUiState(theme = theme, availableNightModes = availableNightModes)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ThemeSettingsUiState(),
      )

  public fun setNightMode(nightMode: NightMode) {
    viewModelScope.launch { themeRepository.setNightMode(nightMode) }
  }
}
