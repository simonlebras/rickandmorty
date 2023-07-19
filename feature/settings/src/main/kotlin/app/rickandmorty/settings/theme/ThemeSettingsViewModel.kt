package app.rickandmorty.settings.theme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.resourcestate.ResourceController
import app.rickandmorty.theme.domain.GetAvailableNightModesUseCase
import app.rickandmorty.theme.domain.GetThemeUseCase
import app.rickandmorty.theme.domain.NightMode
import app.rickandmorty.theme.domain.SetNightModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class ThemeSettingsViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    getAvailableNightModes: GetAvailableNightModesUseCase,
    private val setNightMode: SetNightModeUseCase,
) : ViewModel() {
    private val theme = ResourceController(
        resource = getThemeUseCase(),
    )

    private val availableNightModes = ResourceController(
        resource = suspend { getAvailableNightModes() },
    )

    val uiState: StateFlow<ThemeSettingsUiState> = combine(
        theme.state,
        availableNightModes.state,
    ) { theme, availableNightModes ->
        ThemeSettingsUiState(
            theme = theme,
            availableNightModes = availableNightModes,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = ThemeSettingsUiState(),
        started = WhileViewSubscribed,
    )

    fun setNightMode(nightMode: NightMode) {
        viewModelScope.launch {
            setNightMode.invoke(nightMode)
        }
    }
}
