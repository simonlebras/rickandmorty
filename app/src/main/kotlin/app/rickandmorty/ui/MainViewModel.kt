package app.rickandmorty.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.coroutines.WhileSubscribedOrRetained
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@ContributesIntoMap(AppScope::class)
@ViewModelKey(MainViewModel::class)
class MainViewModel(themeRepository: ThemeRepository) : ViewModel() {
  private val theme = ResourceController(resource = themeRepository.getTheme())

  val uiState: StateFlow<MainUiState> =
    theme.state
      .map { theme -> MainUiState(theme = theme) }
      .stateIn(
        scope = viewModelScope,
        started = WhileSubscribedOrRetained,
        initialValue = MainUiState(),
      )
}
