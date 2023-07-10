package app.rickandmorty.settings.dartheme

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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.rickandmorty.settings.R
import app.rickandmorty.settings.utils.toText
import app.rickandmorty.theme.domain.NightMode

@Composable
internal fun DarkThemeSettingsDialog(
    onDismiss: () -> Unit,
    viewModel: DarkThemeSettingsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ReportDrawnWhen { !uiState.isLoading }

    DarkThemeSettingsDialog(
        uiState = uiState,
        onDismiss = onDismiss,
        onSelectNightNode = viewModel::setNightMode,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DarkThemeSettingsDialog(
    uiState: DarkThemeUiState,
    onDismiss: () -> Unit,
    onSelectNightNode: (NightMode) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(R.string.settings_dark_mode_title),
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                when {
                    uiState.isLoading -> {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(vertical = 16.dp),
                        )
                    }

                    else -> {
                        NightMode.entries.forEachIndexed { index, nightMode ->
                            ThemeItem(
                                text = nightMode.toText(),
                                selected = nightMode == uiState.theme()!!.nightMode,
                                onClick = { onSelectNightNode(nightMode) },
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Text(
                text = stringResource(R.string.dismiss_dialog_button_text),
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
