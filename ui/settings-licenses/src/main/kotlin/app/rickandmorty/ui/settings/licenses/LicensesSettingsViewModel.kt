package app.rickandmorty.ui.settings.licenses

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.cash.molecule.AndroidUiDispatcher
import app.cash.molecule.RecompositionMode.ContextClock
import app.cash.molecule.moleculeFlow
import app.rickandmorty.core.coroutines.WhileViewSubscribed
import app.rickandmorty.core.resourcestate.ResourceController
import app.rickandmorty.data.license.LicenseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
public class LicensesSettingsViewModel @Inject constructor(
    private val licenseRepository: LicenseRepository,
) : ViewModel() {
    private val scope = CoroutineScope(viewModelScope.coroutineContext + AndroidUiDispatcher.Main)

    private val licenses = ResourceController(
        resource = suspend { licenseRepository.getLicense() },
    )

    public val uiState: StateFlow<LicenseSettingsUiState> = moleculeFlow(ContextClock) {
        val licenses by licenses.state.collectAsState()

        LicenseSettingsUiState(
            licenses = licenses,
        )
    }
        .stateIn(
            scope = scope,
            started = WhileViewSubscribed,
            initialValue = LicenseSettingsUiState(),
        )
}
