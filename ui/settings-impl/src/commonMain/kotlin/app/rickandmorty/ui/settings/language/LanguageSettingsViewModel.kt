package app.rickandmorty.ui.settings.language

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.locale.LocaleRepository
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
import kotlinx.coroutines.flow.flow

@Stable
public interface LanguageSettingsStateHolder :
  ActionSuspendingStateMutator<LanguageSettingsAction, LanguageSettingsUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(LanguageSettingsViewModel::class)
public fun interface LanguageSettingsViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): LanguageSettingsViewModel =
    invoke(scope = viewModelCoroutineScope())

  public fun invoke(@Assisted scope: CoroutineScope): LanguageSettingsViewModel
}

public class LanguageSettingsViewModel(
  mutator: ActionSuspendingStateMutator<LanguageSettingsAction, LanguageSettingsUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  LanguageSettingsStateHolder,
  ActionSuspendingStateMutator<LanguageSettingsAction, LanguageSettingsUiState> by mutator {
  @AssistedInject
  public constructor(
    localeRepository: LocaleRepository,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.actionSuspendingStateMutator(
        state = LanguageSettingsUiState().toSnapshotMutable(),
        producer = { state, actions ->
          launchAppLocaleMutations(state = state, localeRepository = localeRepository)
          launchLoadAvailableAppLocalesMutations(state = state, localeRepository = localeRepository)
          actions.launchMutationsIn(
            productionScope = this,
            keySelector = LanguageSettingsAction::key,
          ) {
            when (val action = type()) {
              is LanguageSettingsAction.SelectLocale ->
                action.flow.launchSelectLocaleMutations(localeRepository = localeRepository)
            }
          }
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun Flow<LanguageSettingsAction.SelectLocale>.launchSelectLocaleMutations(
  localeRepository: LocaleRepository,
) = launchedCollect { action -> localeRepository.setAppLocale(action.appLocale) }

context(productionScope: CoroutineScope)
private fun launchAppLocaleMutations(
  state: LanguageSettingsUiState.Mutable,
  localeRepository: LocaleRepository,
) =
  localeRepository.getAppLocale().launchedCollect { appLocale ->
    state.appLocale = appLocale
  }

context(productionScope: CoroutineScope)
private fun launchLoadAvailableAppLocalesMutations(
  state: LanguageSettingsUiState.Mutable,
  localeRepository: LocaleRepository,
) = flow {
  if (state.availableAppLocales == null) emit(localeRepository.getAvailableAppLocales())
}
  .launchedCollect { availableAppLocales -> state.availableAppLocales = availableAppLocales }
