package app.rickandmorty.ui.settings.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.coroutines.WhileSubscribedOrRetained
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.data.theme.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
public class ThemeSettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
) : ViewModel() {
    private val theme = ResourceController(
        resource = themeRepository.getTheme(),
    )

    private val availableNightModes = ResourceController(
        resource = suspend { themeRepository.getAvailableNightModes() },
    )

    public val uiState: StateFlow<ThemeSettingsUiState> = combine(
        theme.state,
        availableNightModes.state,
    ) { theme, availableNightModes ->
        ThemeSettingsUiState(
            theme = theme,
            availableNightModes = availableNightModes,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribedOrRetained,
        initialValue = ThemeSettingsUiState(),
    )

    public fun setNightMode(nightMode: NightMode) {
        viewModelScope.launch {
            themeRepository.setNightMode(nightMode)
        }
    }
}
