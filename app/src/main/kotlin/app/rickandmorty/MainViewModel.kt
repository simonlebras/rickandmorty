package app.rickandmorty

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.data.theme.ThemeRepository
import app.rickandmorty.resourcestate.ResourceController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class MainViewModel @Inject constructor(
    themeRepository: ThemeRepository,
) : ViewModel() {
    private val theme = ResourceController(
        resource = themeRepository.getTheme(),
    )

    val uiState: StateFlow<MainUiState> = theme.state
        .map { theme ->
            MainUiState(theme = theme)
        }.stateIn(
            scope = viewModelScope,
            initialValue = MainUiState(),
            started = WhileViewSubscribed,
        )
}
