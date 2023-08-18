package app.rickandmorty.feature.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.resourcestate.ResourceController
import app.rickandmorty.service.locale.LocaleService
import app.rickandmorty.service.locale.model.Locale
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class LanguageSettingsViewModel @Inject constructor(
    private val localeService: LocaleService,
) : ViewModel() {
    private val applicationLocale = ResourceController(
        resource = localeService.getApplicationLocale(),
    )

    private val availableApplicationLocales = ResourceController(
        resource = suspend { localeService.getAvailableApplicationLocales() },
    )

    val uiState: StateFlow<LanguageSettingsUiState> = combine(
        applicationLocale.state,
        availableApplicationLocales.state,
    ) { applicationLocale, availableApplicationLocales ->
        LanguageSettingsUiState(
            applicationLocale = applicationLocale,
            availableApplicationLocales = availableApplicationLocales,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = LanguageSettingsUiState(),
        started = WhileViewSubscribed,
    )

    fun setApplicationLocale(locale: Locale?) {
        viewModelScope.launch {
            localeService.setApplicationLocale(locale)
        }
    }
}
