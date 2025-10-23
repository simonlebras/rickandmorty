package app.rickandmorty.core.designsystem.component

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.icon.filled.ArrowBack
import app.rickandmorty.core.designsystem.icon.filled.Settings
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.back
import app.rickandmorty.core.l10n.resources.settings_title
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
public fun BackNavButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  NavButton(
    icon = RamIcons.Filled.ArrowBack,
    contentDescription = L10nRes.string.back,
    onClick = onClick,
    modifier = modifier,
  )
}

@Composable
public fun SettingsNavButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
  NavButton(
    icon = RamIcons.Filled.Settings,
    contentDescription = L10nRes.string.settings_title,
    onClick = onClick,
    modifier = modifier,
  )
}

@Composable
private fun NavButton(
  icon: ImageVector,
  contentDescription: StringResource,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  IconButton(onClick = onClick, modifier = modifier) {
    Icon(imageVector = icon, contentDescription = stringResource(contentDescription))
  }
}
