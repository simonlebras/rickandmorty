package app.rickandmorty.ui.settings.license

import androidx.compose.runtime.Stable
import app.rickandmorty.data.license.License
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList

@Stable
@Snapshottable
public interface LicenseSettingsUiState {
  @SnapshotSpec
  public data class Immutable(val licenses: ImmutableList<License>? = null) : LicenseSettingsUiState
}

public companion operator fun LicenseSettingsUiState.invoke(): LicenseSettingsUiState.Immutable =
  LicenseSettingsUiState.Immutable()

public val LicenseSettingsUiState.isLoading: Boolean
  get() = licenses == null
