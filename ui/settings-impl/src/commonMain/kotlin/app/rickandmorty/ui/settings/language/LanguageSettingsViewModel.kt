package app.rickandmorty.ui.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.locale.Locale
import app.rickandmorty.data.locale.LocaleRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@ContributesIntoMap(UiScope::class)
@ViewModelKey(LanguageSettingsViewModel::class)
public class LanguageSettingsViewModel(private val localeRepository: LocaleRepository) :
  ViewModel() {
  private val appLocale = ResourceController(resource = localeRepository.getAppLocale())

  private val availableAppLocales =
    ResourceController(resource = suspend { localeRepository.getAvailableAppLocales() })

  public val uiState: StateFlow<LanguageSettingsUiState> =
    combine(appLocale.state, availableAppLocales.state) { appLocale, availableAppLocales ->
        LanguageSettingsUiState(appLocale = appLocale, availableAppLocales = availableAppLocales)
      }
      .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LanguageSettingsUiState(),
      )

  public fun setAppLocale(locale: Locale?) {
    viewModelScope.launch { localeRepository.setAppLocale(locale) }
  }
}
