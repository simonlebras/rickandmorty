package app.rickandmorty.settings.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.rickandmorty.coroutines.WhileViewSubscribed
import app.rickandmorty.locale.domain.GetApplicationLocaleUseCase
import app.rickandmorty.resourcestate.ResourceController
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
internal class MainSettingsViewModel @Inject constructor(
    getApplicationLocale: GetApplicationLocaleUseCase,
) : ViewModel() {
    private val applicationLocale = ResourceController(
        resource = getApplicationLocale(),
    )

    val uiState: StateFlow<MainSettingsUiState> = applicationLocale.state
        .map { applicationLocale ->
            MainSettingsUiState(applicationLocale = applicationLocale)
        }.stateIn(
            scope = viewModelScope,
            initialValue = MainSettingsUiState(),
            started = WhileViewSubscribed,
        )
}
