package app.rickandmorty.ui

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.coroutines.WhileSubscribedOrRetained
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.theme.ThemeRepository
import com.tunjid.mutator.coroutines.SuspendingStateMutator
import com.tunjid.mutator.coroutines.launchedCollect
import com.tunjid.mutator.coroutines.suspendingStateMutator
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.coroutines.CoroutineScope

@Stable interface MainStateHolder : SuspendingStateMutator<MainUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(MainViewModel::class)
fun interface MainViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): MainViewModel =
    invoke(scope = viewModelCoroutineScope())

  fun invoke(@Assisted scope: CoroutineScope): MainViewModel
}

class MainViewModel(mutator: SuspendingStateMutator<MainUiState>, scope: CoroutineScope) :
  ViewModel(viewModelScope = scope),
  MainStateHolder,
  SuspendingStateMutator<MainUiState> by mutator {
  @AssistedInject
  constructor(
    themeRepository: ThemeRepository,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.suspendingStateMutator(
        state = MainUiState().toSnapshotMutable(),
        started = WhileSubscribedOrRetained,
        producer = { state ->
          launchThemeMutations(state = state, themeRepository = themeRepository)
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun launchThemeMutations(state: MainUiState.Mutable, themeRepository: ThemeRepository) =
  themeRepository.getTheme().launchedCollect { theme -> state.theme = theme }
