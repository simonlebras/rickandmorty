package app.rickandmorty.ui.debugdrawer

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.debug.DebugSettingsRepository
import coil3.ImageLoader
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.launch

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class DebugDrawerViewModel(
  private val debugSettingsRepository: DebugSettingsRepository,
  private val imageLoader: ImageLoader,
  @IODispatcher private val ioDispatcher: CoroutineContext,
  appInfo: AppInfo,
) : ViewModel() {

  private val versionName = appInfo.versionName
  private val versionCode = appInfo.versionCode
  private val packageName = appInfo.packageName

  public val state: State<DebugDrawerUiState> =
    transform(
      initialValue =
        DebugDrawerUiState(
          versionName = versionName,
          versionCode = versionCode,
          packageName = packageName,
          debugSettings = debugSettingsRepository.debugSettings.value,
        ),
      scope = viewModelScope,
    ) { previous ->
      DebugDrawerUiState(
        versionName = versionName,
        versionCode = versionCode,
        packageName = packageName,
        debugSettings = debugSettingsRepository.debugSettings.value ?: return@transform previous,
      )
    }

  public fun setKeylineOverlayEnabled(enabled: Boolean) {
    debugSettingsRepository.setKeylineOverlayEnabled(enabled)
  }

  public fun setLookaheadDebuggingEnabled(enabled: Boolean) {
    debugSettingsRepository.setLookaheadDebuggingEnabled(enabled)
  }

  public fun clearImageCache() {
    viewModelScope.launch(ioDispatcher) {
      imageLoader.memoryCache?.clear()
      imageLoader.diskCache?.clear()
    }
  }
}
