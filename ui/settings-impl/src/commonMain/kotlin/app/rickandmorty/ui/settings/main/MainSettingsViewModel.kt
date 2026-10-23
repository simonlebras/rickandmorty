package app.rickandmorty.ui.settings.main

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.locale.LocaleRepository
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
public interface MainSettingsStateHolder :
  ActionSuspendingStateMutator<MainSettingsAction, MainSettingsUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(MainSettingsViewModel::class)
public fun interface MainSettingsViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): MainSettingsViewModel =
    invoke(scope = viewModelCoroutineScope())

  public fun invoke(@Assisted scope: CoroutineScope): MainSettingsViewModel
}

public class MainSettingsViewModel(
  mutator: ActionSuspendingStateMutator<MainSettingsAction, MainSettingsUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  MainSettingsStateHolder,
  ActionSuspendingStateMutator<MainSettingsAction, MainSettingsUiState> by mutator {
  @AssistedInject
  public constructor(
    themeRepository: ThemeRepository,
    localeRepository: LocaleRepository,
    appInfo: AppInfo,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.actionSuspendingStateMutator(
        state = MainSettingsUiState(versionName = appInfo.versionName).toSnapshotMutable(),
        producer = { state, actions ->
          launchThemeMutations(state = state, themeRepository = themeRepository)
          launchAppLocaleMutations(state = state, localeRepository = localeRepository)
          actions.launchMutationsIn(
            productionScope = this,
            keySelector = MainSettingsAction::key,
          ) {
            when (val action = type()) {
              is MainSettingsAction.SetUseDynamicColor ->
                action.flow.launchSetUseDynamicColorMutations(themeRepository = themeRepository)
            }
          }
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun Flow<MainSettingsAction.SetUseDynamicColor>.launchSetUseDynamicColorMutations(
  themeRepository: ThemeRepository,
) = launchedCollect { action -> themeRepository.setUseDynamicColor(action.useDynamicColor) }

context(productionScope: CoroutineScope)
private fun launchThemeMutations(
  state: MainSettingsUiState.Mutable,
  themeRepository: ThemeRepository,
) = themeRepository.getTheme().launchedCollect { theme -> state.theme = theme }

context(productionScope: CoroutineScope)
private fun launchAppLocaleMutations(
  state: MainSettingsUiState.Mutable,
  localeRepository: LocaleRepository,
) =
  localeRepository.getAppLocale().launchedCollect { appLocale ->
    state.appLocale = appLocale
  }
