package app.rickandmorty.feature.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.data.theme.ThemeRepository
import app.rickandmorty.locale.domain.GetApplicationLocaleUseCase
import app.rickandmorty.resourcestate.ResourceController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainSettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    getApplicationLocale: GetApplicationLocaleUseCase,
) : ViewModel() {
    private val theme = ResourceController(
        resource = themeRepository.getTheme(),
    )

    private val applicationLocale = ResourceController(
        resource = getApplicationLocale(),
    )

    val uiState: StateFlow<MainSettingsUiState> = combine(
        theme.state,
        applicationLocale.state,
    ) { theme, applicationLocale ->
        MainSettingsUiState(
            theme = theme,
            applicationLocale = applicationLocale,
        )
    }.stateIn(
        scope = viewModelScope,
        initialValue = MainSettingsUiState(),
        started = WhileViewSubscribed,
    )

    fun setUseDynamicColor(useDynamicColor: Boolean) {
        viewModelScope.launch {
            themeRepository.setUseDynamicColor(useDynamicColor)
        }
    }
}
