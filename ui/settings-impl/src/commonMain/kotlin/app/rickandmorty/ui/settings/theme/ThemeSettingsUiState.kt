package app.rickandmorty.ui.settings.theme

import androidx.compose.runtime.Stable
import app.rickandmorty.data.theme.NightMode
import app.rickandmorty.data.theme.Theme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
public data class ThemeSettingsUiState(
  val theme: Theme? = null,
  val availableNightModes: ImmutableList<NightMode> = persistentListOf(),
)

public val ThemeSettingsUiState.isLoading: Boolean
  get() = theme == null
