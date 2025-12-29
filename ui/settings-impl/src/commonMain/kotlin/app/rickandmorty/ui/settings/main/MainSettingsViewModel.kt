package app.rickandmorty.ui.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.locale.LocaleRepository
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(UiScope::class)
@ViewModelKey(MainSettingsViewModel::class)
public class MainSettingsViewModel(
  private val themeRepository: ThemeRepository,
  localeRepository: LocaleRepository,
  appInfo: AppInfo,
) : ViewModel() {
  private val theme = ResourceController(resource = themeRepository.getTheme())

  private val appLocale = ResourceController(resource = localeRepository.getAppLocale())

  public val uiState: StateFlow<MainSettingsUiState> =
    combine(theme.state, appLocale.state) { theme, appLocale ->
        MainSettingsUiState(theme = theme, appLocale = appLocale, versionName = appInfo.versionName)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = MainSettingsUiState(),
      )

  public fun setUseDynamicColor(useDynamicColor: Boolean) {
    viewModelScope.launch { themeRepository.setUseDynamicColor(useDynamicColor) }
  }
}
