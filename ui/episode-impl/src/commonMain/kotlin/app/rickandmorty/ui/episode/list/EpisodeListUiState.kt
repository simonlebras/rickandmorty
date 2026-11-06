package app.rickandmorty.ui.episode.list

import androidx.compose.runtime.Stable
import app.rickandmorty.core.tiling.TilingState
import app.rickandmorty.core.tiling.tilingData
import app.rickandmorty.core.ui.UiMessage
import app.rickandmorty.data.episode.Episode
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
@Snapshottable
public interface EpisodeListUiState : TilingState<Episode> {
  @SnapshotSpec
  public data class Immutable(
    override val tilingData: TilingState.Data<Episode>,
    val messages: ImmutableList<UiMessage<EpisodeListAction>> = persistentListOf(),
  ) : EpisodeListUiState
}

public companion operator fun EpisodeListUiState.invoke(
  currentPage: Int,
): EpisodeListUiState.Immutable =
  EpisodeListUiState.Immutable(tilingData = tilingData(currentPage = currentPage))

public sealed class EpisodeListAction(public val key: String) {
  public data class Tile(val action: TilingState.Action) : EpisodeListAction(key = "Tile")

  public data class MessageShown(val message: UiMessage<EpisodeListAction>) :
    EpisodeListAction(key = "MessageShown")
}
