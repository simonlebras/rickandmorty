package app.rickandmorty.feature.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.coroutines.WhileSubscribedOrRetained
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.locale.LocaleRepository
import app.rickandmorty.data.model.Locale
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class LanguageSettingsViewModel @Inject constructor(
    private val localeRepository: LocaleRepository,
) : ViewModel() {
    private val appLocale = ResourceController(
        resource = localeRepository.getAppLocale(),
    )

    private val availableAppLocales = ResourceController(
        resource = suspend { localeRepository.getAvailableAppLocales() },
    )

    val uiState: StateFlow<LanguageSettingsUiState> = combine(
        appLocale.state,
        availableAppLocales.state,
    ) { appLocale, availableAppLocales ->
        LanguageSettingsUiState(
            appLocale = appLocale,
            availableAppLocales = availableAppLocales,
        )
    }.stateIn(
        scope = viewModelScope,
        started = WhileSubscribedOrRetained,
        initialValue = LanguageSettingsUiState(),
    )

    fun setAppLocale(locale: Locale?) {
        viewModelScope.launch {
            localeRepository.setAppLocale(locale)
        }
    }
}
