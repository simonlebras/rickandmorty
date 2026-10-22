package app.rickandmorty.ui.settings.language

import androidx.compose.runtime.Stable
import app.rickandmorty.data.locale.AppLocale
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList

@Stable
@Snapshottable
public interface LanguageSettingsUiState {
  @SnapshotSpec
  public data class Immutable(
    val appLocale: AppLocale? = null,
    val availableAppLocales: ImmutableList<AppLocale>? = null,
  ) : LanguageSettingsUiState
}

public companion operator fun LanguageSettingsUiState.invoke(): LanguageSettingsUiState.Immutable =
  LanguageSettingsUiState.Immutable()

public val LanguageSettingsUiState.isLoading: Boolean
  get() = appLocale == null || availableAppLocales == null

public sealed class LanguageSettingsAction(public val key: String) {
  public data class SelectLocale(val appLocale: AppLocale) :
    LanguageSettingsAction(key = "SelectLocale")
}
