package app.rickandmorty.ui.debugdrawer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.debug.DebugSettingsRepository
import coil3.ImageLoader
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(UiScope::class)
@ViewModelKey
internal class DebugDrawerViewModel(
  appInfo: AppInfo,
  private val debugSettingsRepository: DebugSettingsRepository,
  private val imageLoader: ImageLoader,
  @IODispatcher private val ioDispatcher: CoroutineContext,
) : ViewModel() {
  private val debugSettings =
    ResourceController(resource = debugSettingsRepository.getDebugSettings())

  private val initialUiState =
    DebugDrawerUiState(
      versionName = appInfo.versionName,
      versionCode = appInfo.versionCode,
      packageName = appInfo.packageName,
    )

  val uiState: StateFlow<DebugDrawerUiState> =
    debugSettings.state
      .map { debugSettings -> initialUiState.copy(debugSettings = debugSettings) }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initialUiState,
      )

  fun setKeylineOverlayEnabled(enabled: Boolean) {
    viewModelScope.launch {
      debugSettingsRepository.setKeylineOverlayEnabled(enabled)
    }
  }

  fun setLookaheadDebuggingEnabled(enabled: Boolean) {
    viewModelScope.launch {
      debugSettingsRepository.setLookaheadDebuggingEnabled(enabled)
    }
  }

  fun clearImageCache() {
    viewModelScope.launch(ioDispatcher) {
      imageLoader.memoryCache?.clear()
      imageLoader.diskCache?.clear()
    }
  }
}
