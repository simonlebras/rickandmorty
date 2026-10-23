package app.rickandmorty.ui.settings.theme

import androidx.compose.runtime.Stable
import app.rickandmorty.data.theme.NightMode
import app.rickandmorty.data.theme.Theme
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList

@Stable
@Snapshottable
public interface ThemeSettingsUiState {
  @SnapshotSpec
  public data class Immutable(
    val theme: Theme? = null,
    val availableNightModes: ImmutableList<NightMode>,
  ) : ThemeSettingsUiState
}

public companion operator fun ThemeSettingsUiState.invoke(
  availableNightModes: ImmutableList<NightMode>,
): ThemeSettingsUiState.Immutable =
  ThemeSettingsUiState.Immutable(availableNightModes = availableNightModes)

public val ThemeSettingsUiState.isLoading: Boolean
  get() = theme == null

public sealed class ThemeSettingsAction(public val key: String) {
  public data class SelectNightMode(val nightMode: NightMode) :
    ThemeSettingsAction(key = "SelectNightMode")
}
