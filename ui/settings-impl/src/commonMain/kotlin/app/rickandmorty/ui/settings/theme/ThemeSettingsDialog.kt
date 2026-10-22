package app.rickandmorty.ui.settings.theme

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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.dismiss
import app.rickandmorty.core.l10n.resources.settings_theme_title
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.ui.settings.common.util.label
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun ThemeSettingsDialog(
  onDismiss: () -> Unit,
  stateHolder: ThemeSettingsStateHolder = metroViewModel<ThemeSettingsViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  ReportDrawnWhen { !uiState.isLoading }

  ThemeSettingsDialog(
    uiState = uiState,
    onDismiss = onDismiss,
    actions = stateHolder.accept,
  )
}

@Composable
private fun ThemeSettingsDialog(
  uiState: ThemeSettingsUiState,
  onDismiss: () -> Unit,
  actions: (ThemeSettingsAction) -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = { Text(text = stringResource(L10nRes.string.settings_theme_title)) },
    text = {
      Column(modifier = Modifier.selectableGroup().verticalScroll(rememberScrollState())) {
        when (val theme = uiState.theme) {
          null -> {
            CircularProgressIndicator(modifier = Modifier.padding(vertical = 16.dp))
          }

          else -> {
            val currentNightMode = theme.nightMode
            uiState.availableNightModes.forEach { nightMode ->
              key(nightMode) {
                ThemeItem(
                  text = stringResource(nightMode.label),
                  selected = nightMode == currentNightMode,
                  onClick = { actions(ThemeSettingsAction.SelectNightMode(nightMode)) },
                )
              }
            }
          }
        }
      }
    },
    confirmButton = {
      Text(
        text = stringResource(L10nRes.string.dismiss),
        modifier = Modifier.clickable { onDismiss() },
      )
    },
  )
}

@Composable
private fun ThemeItem(text: String, selected: Boolean, onClick: () -> Unit) {
  Row(
    modifier =
      Modifier.fillMaxWidth()
        .selectable(selected = selected, role = Role.RadioButton, onClick = onClick)
        .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    RadioButton(selected = selected, onClick = null)

    Spacer(modifier = Modifier.width(8.dp))

    Text(text)
  }
}
