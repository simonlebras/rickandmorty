package app.rickandmorty.data.character

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

public interface CharacterRepository {
  public fun getPagedCharacters(config: PagingConfig): Flow<PagingData<Character>>
}
