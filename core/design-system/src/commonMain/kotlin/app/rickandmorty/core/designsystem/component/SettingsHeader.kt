package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp

/**
 * A settings section header with a transparent background so that it blends into any container,
 * whether a screen or a drawer sheet.
 */
@Composable
public fun SettingsHeader(text: String, modifier: Modifier = Modifier) {
  Text(
    text = text,
    modifier =
      modifier
        .semantics { heading() }
        .defaultMinSize(minHeight = 32.dp)
        .padding(start = 16.dp, top = 8.dp, end = 24.dp, bottom = 8.dp),
    style = MaterialTheme.typography.titleSmall,
  )
}
