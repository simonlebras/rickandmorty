package app.rickandmorty.ui.settings.language

import androidx.compose.runtime.Stable
import app.rickandmorty.data.locale.AppLocale
import kotlinx.collections.immutable.ImmutableList

@Stable
public data class LanguageSettingsUiState(
  val appLocale: AppLocale? = null,
  val availableAppLocales: ImmutableList<AppLocale>? = null,
)

public val LanguageSettingsUiState.isLoading: Boolean
  get() = appLocale == null || availableAppLocales == null
