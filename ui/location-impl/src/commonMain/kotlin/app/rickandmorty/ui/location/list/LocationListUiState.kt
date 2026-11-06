package app.rickandmorty.ui.location.list

import androidx.compose.runtime.Stable
import app.rickandmorty.core.tiling.TilingState
import app.rickandmorty.core.tiling.tilingData
import app.rickandmorty.core.ui.UiMessage
import app.rickandmorty.data.location.Location
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
@Snapshottable
public interface LocationListUiState : TilingState<Location> {
  @SnapshotSpec
  public data class Immutable(
    override val tilingData: TilingState.Data<Location>,
    val messages: ImmutableList<UiMessage<LocationListAction>> = persistentListOf(),
  ) : LocationListUiState
}

public companion operator fun LocationListUiState.invoke(
  currentPage: Int,
): LocationListUiState.Immutable =
  LocationListUiState.Immutable(tilingData = tilingData(currentPage = currentPage))

public sealed class LocationListAction(public val key: String) {
  public data class Tile(val action: TilingState.Action) : LocationListAction(key = "Tile")

  public data class MessageShown(val message: UiMessage<LocationListAction>) :
    LocationListAction(key = "MessageShown")
}
