package app.rickandmorty.settings.dartheme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.resourcestate.ResourceController
import app.rickandmorty.theme.domain.GetThemeUseCase
import app.rickandmorty.theme.domain.NightMode
import app.rickandmorty.theme.domain.SetNightModeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class DarkThemeSettingsViewModel @Inject constructor(
    getThemeUseCase: GetThemeUseCase,
    private val setNightMode: SetNightModeUseCase,
) : ViewModel() {
    private val theme = ResourceController(
        resource = getThemeUseCase(),
    )

    val uiState: StateFlow<DarkThemeUiState> = theme.state
        .map { theme ->
            DarkThemeUiState(theme = theme)
        }.stateIn(
            scope = viewModelScope,
            initialValue = DarkThemeUiState(),
            started = WhileViewSubscribed,
        )

    fun setNightMode(nightMode: NightMode) {
        viewModelScope.launch {
            setNightMode.invoke(nightMode)
        }
    }
}
