package app.rickandmorty.ui.settings.common.util

import androidx.compose.runtime.Composable
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.settings_language_system_default
import app.rickandmorty.data.locale.AppLocale
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun AppLocale.label(): String =
  when (this) {
    AppLocale.System -> stringResource(L10nRes.string.settings_language_system_default)
    is AppLocale.Specific -> displayName
  }
