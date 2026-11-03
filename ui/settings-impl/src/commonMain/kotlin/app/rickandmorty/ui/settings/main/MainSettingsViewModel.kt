package app.rickandmorty.ui.settings.main

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.appinfo.AppInfo
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.locale.LocaleRepository
import app.rickandmorty.data.theme.ThemeRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class MainSettingsViewModel(
  private val themeRepository: ThemeRepository,
  private val localeRepository: LocaleRepository,
  appInfo: AppInfo,
) : ViewModel() {

  private val versionName = appInfo.versionName

  public val state: State<MainSettingsUiState> =
    transform(
      initialValue =
        MainSettingsUiState(
          theme = themeRepository.theme.value,
          appLocale = localeRepository.appLocale.value,
          versionName = versionName,
        ),
      scope = viewModelScope,
    ) { previous ->
      MainSettingsUiState(
        theme = themeRepository.theme.value ?: return@transform previous,
        appLocale = localeRepository.appLocale.value,
        versionName = versionName,
      )
    }

  public fun setUseDynamicColor(useDynamicColor: Boolean) {
    themeRepository.setUseDynamicColor(useDynamicColor)
  }
}
