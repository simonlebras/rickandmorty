package app.rickandmorty.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.locale.domain.GetApplicationLocaleUseCase
import app.rickandmorty.locale.domain.GetAvailableLocalesUseCase
import app.rickandmorty.locale.domain.Locale
import app.rickandmorty.locale.domain.SetApplicationLocaleUseCase
import app.rickandmorty.resourcestate.ResourceController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class LanguageSettingsViewModel @Inject constructor(
    getApplicationLocale: GetApplicationLocaleUseCase,
    private val setApplicationLocale: SetApplicationLocaleUseCase,
    getAvailableLocales: GetAvailableLocalesUseCase,
) : ViewModel() {
    private val applicationLocale = ResourceController(
        resource = getApplicationLocale(),
    )

    private val availableLocales = ResourceController(
        resource = suspend { getAvailableLocales() },
    )

    val uiState: StateFlow<LanguageSettingsUiState> = combine(
        applicationLocale.state,
        availableLocales.state,
    ) { applicationLocale, availableLocales ->
        LanguageSettingsUiState(
            applicationLocale = applicationLocale,
            availableLocales = availableLocales,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = LanguageSettingsUiState(),
        started = WhileViewSubscribed,
    )

    fun setApplicationLocale(locale: Locale?) {
        viewModelScope.launch {
            setApplicationLocale.invoke(locale)
        }
    }
}
