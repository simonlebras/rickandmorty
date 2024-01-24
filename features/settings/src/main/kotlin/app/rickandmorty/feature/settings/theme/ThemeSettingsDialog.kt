package app.rickandmorty.feature.settings.theme

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastForEach
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.core.metrics.TrackScrollJank
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.data.model.NightMode
import app.rickandmorty.feature.settings.R
import app.rickandmorty.feature.settings.util.label

@Composable
internal fun ThemeSettingsDialog(
    onDismiss: () -> Unit,
    viewModel: ThemeSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawnWhen { !uiState.isLoading }

    ThemeSettingsDialog(
        uiState = uiState,
        onDismiss = onDismiss,
        onSelectNightMode = viewModel::setNightMode,
    )
}

@Composable
private fun ThemeSettingsDialog(
    uiState: ThemeSettingsUiState,
    onDismiss: () -> Unit,
    onSelectNightMode: (NightMode) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = stringResource(R.string.settings_theme_title))
        },
        text = {
            val scrollState = rememberScrollState()

            TrackScrollJank(
                scrollableState = scrollState,
                stateName = "themeSettings:dialog",
            )

            Column(
                modifier = Modifier
                    .selectableGroup()
                    .verticalScroll(scrollState),
            ) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    else -> {
                        val currentNightMode = uiState.theme()!!.nightMode
                        uiState.availableNightModes()!!.fastForEach { nightMode ->
                            key(nightMode) {
                                ThemeItem(
                                    text = stringResource(nightMode.label),
                                    selected = nightMode == currentNightMode,
                                    onClick = { onSelectNightMode(nightMode) },
                                )
                            }
                        }
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(UiR.string.dismiss),
                modifier = Modifier.clickable { onDismiss() },
            )
        },
    )
}

@Composable
private fun ThemeItem(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(text)
    }
}
