package app.rickandmorty.ui.settings.main

import androidx.compose.runtime.Stable
import app.rickandmorty.data.locale.AppLocale
import app.rickandmorty.data.theme.Theme
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable

@Stable
@Snapshottable
public interface MainSettingsUiState {
  @SnapshotSpec
  public data class Immutable(
    val theme: Theme? = null,
    val appLocale: AppLocale? = null,
    val versionName: String = "",
  ) : MainSettingsUiState
}

public companion operator fun MainSettingsUiState.invoke(
  versionName: String,
): MainSettingsUiState.Immutable = MainSettingsUiState.Immutable(versionName = versionName)

public val MainSettingsUiState.isLoading: Boolean
  get() = theme == null || appLocale == null

public sealed class MainSettingsAction(public val key: String) {
  public data class SetUseDynamicColor(val useDynamicColor: Boolean) :
    MainSettingsAction(key = "SetUseDynamicColor")
}
