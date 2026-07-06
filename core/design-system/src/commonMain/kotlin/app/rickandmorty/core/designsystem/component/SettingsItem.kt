package app.rickandmorty.core.designsystem.component

import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * A settings list item with a transparent container so that it blends into any background, whether
 * a screen or a drawer sheet.
 */
@Composable
public fun SettingsItem(
  headlineContent: @Composable () -> Unit,
  modifier: Modifier = Modifier,
  supportingContent: @Composable (() -> Unit)? = null,
  trailingContent: @Composable (() -> Unit)? = null,
) {
  ListItem(
    headlineContent = headlineContent,
    modifier = modifier,
    supportingContent = supportingContent,
    trailingContent = trailingContent,
    colors = ListItemDefaults.colors(containerColor = Color.Transparent),
  )
}
