package app.rickandmorty.ui.character.list

import androidx.compose.runtime.Stable
import app.rickandmorty.core.tiling.TilingState
import app.rickandmorty.core.tiling.tilingData
import app.rickandmorty.core.ui.UiMessage
import app.rickandmorty.data.character.Character
import com.tunjid.snapshottable.SnapshotSpec
import com.tunjid.snapshottable.Snapshottable
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
@Snapshottable
public interface CharacterListUiState : TilingState<Character> {
  @SnapshotSpec
  public data class Immutable(
    override val tilingData: TilingState.Data<Character>,
    val messages: ImmutableList<UiMessage<CharacterListAction>> = persistentListOf(),
  ) : CharacterListUiState
}

public companion operator fun CharacterListUiState.invoke(
  currentPage: Int,
): CharacterListUiState.Immutable =
  CharacterListUiState.Immutable(tilingData = tilingData(currentPage = currentPage))

public sealed class CharacterListAction(public val key: String) {
  public data class Tile(val action: TilingState.Action) : CharacterListAction(key = "Tile")

  public data class MessageShown(val message: UiMessage<CharacterListAction>) :
    CharacterListAction(key = "MessageShown")
}
