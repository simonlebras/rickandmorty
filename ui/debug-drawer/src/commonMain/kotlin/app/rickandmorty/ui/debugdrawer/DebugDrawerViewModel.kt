package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.debug.DebugSettingsRepository
import coil3.ImageLoader
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
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

@Stable
public interface DebugDrawerStateHolder :
  ActionSuspendingStateMutator<DebugDrawerAction, DebugDrawerUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(DebugDrawerViewModel::class)
public fun interface DebugDrawerViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): DebugDrawerViewModel =
    invoke(scope = viewModelCoroutineScope())

  public fun invoke(@Assisted scope: CoroutineScope): DebugDrawerViewModel
}

public class DebugDrawerViewModel(
  mutator: ActionSuspendingStateMutator<DebugDrawerAction, DebugDrawerUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  DebugDrawerStateHolder,
  ActionSuspendingStateMutator<DebugDrawerAction, DebugDrawerUiState> by mutator {
  @AssistedInject
  public constructor(
    appInfo: AppInfo,
    debugSettingsRepository: DebugSettingsRepository,
    imageLoader: ImageLoader,
    @IODispatcher ioDispatcher: CoroutineContext,
    @Assisted scope: CoroutineScope,
  ) : this(
    mutator =
      scope.actionSuspendingStateMutator(
        state =
          DebugDrawerUiState(
              versionName = appInfo.versionName,
              versionCode = appInfo.versionCode,
              packageName = appInfo.packageName,
            )
            .toSnapshotMutable(),
        producer = { state, actions ->
          launchDebugSettingsMutations(
            state = state,
            debugSettingsRepository = debugSettingsRepository,
          )
          actions.launchMutationsIn(productionScope = this, keySelector = DebugDrawerAction::key) {
            when (val action = type()) {
              is DebugDrawerAction.SetKeylineOverlayEnabled ->
                action.flow.launchSetKeylineOverlayEnabledMutations(
                  debugSettingsRepository = debugSettingsRepository,
                )
              is DebugDrawerAction.SetLookaheadDebuggingEnabled ->
                action.flow.launchSetLookaheadDebuggingEnabledMutations(
                  debugSettingsRepository = debugSettingsRepository,
                )
              is DebugDrawerAction.ClearImageCache ->
                action.flow.launchClearImageCacheMutations(
                  imageLoader = imageLoader,
                  ioDispatcher = ioDispatcher,
                )
            }
          }
        },
      ),
    scope = scope,
  )
}

context(productionScope: CoroutineScope)
private fun Flow<DebugDrawerAction.SetKeylineOverlayEnabled>
  .launchSetKeylineOverlayEnabledMutations(debugSettingsRepository: DebugSettingsRepository) =
  launchedCollect { action ->
    debugSettingsRepository.setKeylineOverlayEnabled(action.enabled)
  }

context(productionScope: CoroutineScope)
private fun Flow<DebugDrawerAction.SetLookaheadDebuggingEnabled>
  .launchSetLookaheadDebuggingEnabledMutations(debugSettingsRepository: DebugSettingsRepository) =
  launchedCollect { action ->
    debugSettingsRepository.setLookaheadDebuggingEnabled(action.enabled)
  }

context(productionScope: CoroutineScope)
private fun Flow<DebugDrawerAction.ClearImageCache>.launchClearImageCacheMutations(
  imageLoader: ImageLoader,
  ioDispatcher: CoroutineContext,
) = launchedCollect {
  withContext(ioDispatcher) {
    imageLoader.memoryCache?.clear()
    imageLoader.diskCache?.clear()
  }
}

context(productionScope: CoroutineScope)
private fun launchDebugSettingsMutations(
  state: DebugDrawerUiState.Mutable,
  debugSettingsRepository: DebugSettingsRepository,
) =
  debugSettingsRepository.getDebugSettings().launchedCollect { debugSettings ->
    state.debugSettings = debugSettings
  }
