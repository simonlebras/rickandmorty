package app.rickandmorty.ui.settings.language

import androidx.appstate.transform.transform
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.locale.AppLocale
import app.rickandmorty.data.locale.LocaleRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.collections.immutable.ImmutableList

@ContributesIntoMap(UiScope::class)
@ViewModelKey
public class LanguageSettingsViewModel(
  private val localeRepository: LocaleRepository,
) : ViewModel() {

  public val state: State<LanguageSettingsUiState> =
    transform(
      initialValue =
        LanguageSettingsUiState(
          appLocale = localeRepository.appLocale.value,
        ),
      scope = viewModelScope,
    ) { previous ->
      val availableLocales by
        produceState<ImmutableList<AppLocale>?>(null) {
          value = localeRepository.getAvailableAppLocales()
        }
      LanguageSettingsUiState(
        appLocale = localeRepository.appLocale.value,
        availableAppLocales = availableLocales,
      )
    }

  public fun selectLocale(appLocale: AppLocale) {
    localeRepository.setAppLocale(appLocale)
  }
}
