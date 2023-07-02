package app.rickandmorty.settings.language

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.locale.domain.GetApplicationLocaleUseCase
import app.rickandmorty.locale.domain.GetSupportedLocalesUseCase
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
    getSupportedLocales: GetSupportedLocalesUseCase,
) : ViewModel() {
    private val applicationLocale = ResourceController(
        resource = getApplicationLocale(),
    )

    private val supportedLocales = ResourceController(
        resource = suspend { getSupportedLocales() },
    )

    val uiState: StateFlow<LanguageSettingsUiState> = combine(
        applicationLocale.state,
        supportedLocales.state,
    ) { applicationLocale, supportedLocales ->
        LanguageSettingsUiState(
            applicationLocale = applicationLocale,
            supportedLocales = supportedLocales,
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
