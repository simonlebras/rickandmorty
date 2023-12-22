package app.rickandmorty.feature.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.core.coroutines.WhileViewSubscribed
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.locale.LocaleRepository
import app.rickandmorty.data.theme.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainSettingsViewModel @Inject constructor(
    private val themeRepository: ThemeRepository,
    localeRepository: LocaleRepository,
) : ViewModel() {
    private val theme = ResourceController(
        resource = themeRepository.getTheme(),
    )

    private val appLocale = ResourceController(
        resource = localeRepository.getAppLocale(),
    )

    val uiState: StateFlow<MainSettingsUiState> = combine(
        theme.state,
        appLocale.state,
    ) { theme, appLocale ->
        MainSettingsUiState(
            theme = theme,
            appLocale = appLocale,
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
