package app.rickandmorty.ui.settings.main

import androidx.compose.runtime.Stable
import app.rickandmorty.data.locale.AppLocale
import app.rickandmorty.data.theme.Theme

@Stable
public data class MainSettingsUiState(
  val theme: Theme? = null,
  val appLocale: AppLocale? = null,
  val versionName: String = "",
)

public val MainSettingsUiState.isLoading: Boolean
  get() = theme == null || appLocale == null
