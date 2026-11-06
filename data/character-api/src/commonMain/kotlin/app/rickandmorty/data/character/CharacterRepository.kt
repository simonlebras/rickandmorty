package app.rickandmorty.data.character

import app.rickandmorty.core.paging.Page
import kotlinx.coroutines.flow.Flow

public interface CharacterRepository {
  public fun getCharacterPage(page: Int): Flow<Page<Character>>

  public suspend fun refreshCharacters()
}
