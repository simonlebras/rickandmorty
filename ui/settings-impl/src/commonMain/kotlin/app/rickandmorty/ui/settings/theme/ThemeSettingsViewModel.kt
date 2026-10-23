package app.rickandmorty.ui.settings.theme

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.theme.ThemeRepository
import com.tunjid.mutator.coroutines.ActionSuspendingStateMutator
import com.tunjid.mutator.coroutines.actionSuspendingStateMutator
import com.tunjid.mutator.coroutines.launchMutationsIn
import com.tunjid.mutator.coroutines.launchedCollect
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Stable
public interface ThemeSettingsStateHolder :
  ActionSuspendingStateMutator<ThemeSettingsAction, ThemeSettingsUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(ThemeSettingsViewModel::class)
public fun interface ThemeSettingsViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): ThemeSettingsViewModel =
    invoke(scope = viewModelCoroutineScope())

  public fun invoke(@Assisted scope: CoroutineScope): ThemeSettingsViewModel
}

public class ThemeSettingsViewModel(
  mutator: ActionSuspendingStateMutator<ThemeSettingsAction, ThemeSettingsUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  ThemeSettingsStateHolder,
  ActionSuspendingStateMutator<ThemeSettingsAction, ThemeSettingsUiState> by mutator {
  @AssistedInject
  public constructor(
    themeRepository: ThemeRepository,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.actionSuspendingStateMutator(
        state =
          ThemeSettingsUiState(availableNightModes = themeRepository.getAvailableNightModes())
            .toSnapshotMutable(),
        producer = { state, actions ->
          launchThemeMutations(state = state, themeRepository = themeRepository)
          actions.launchMutationsIn(
            productionScope = this,
            keySelector = ThemeSettingsAction::key,
          ) {
            when (val action = type()) {
              is ThemeSettingsAction.SelectNightMode ->
                action.flow.launchSelectNightModeMutations(themeRepository = themeRepository)
            }
          }
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun Flow<ThemeSettingsAction.SelectNightMode>.launchSelectNightModeMutations(
  themeRepository: ThemeRepository,
) = launchedCollect { action -> themeRepository.setNightMode(action.nightMode) }

context(productionScope: CoroutineScope)
private fun launchThemeMutations(
  state: ThemeSettingsUiState.Mutable,
  themeRepository: ThemeRepository,
) = themeRepository.getTheme().launchedCollect { theme -> state.theme = theme }
