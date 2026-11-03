package app.rickandmorty.ui

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@ContributesIntoMap(UiScope::class)
@ViewModelKey
class MainViewModel(
  themeRepository: ThemeRepository,
) : ViewModel() {

  val state: State<MainUiState> =
    transform(
      initialValue = MainUiState(theme = themeRepository.theme.value),
      scope = viewModelScope,
    ) { previous ->
      val theme = themeRepository.theme.value ?: return@transform previous
      MainUiState(theme = theme)
    }
}
