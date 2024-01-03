package app.rickandmorty.data.character

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.rickandmorty.data.model.Character
import kotlinx.coroutines.flow.Flow

public interface CharacterRepository {
    public fun getPagedCharacters(config: PagingConfig): Flow<PagingData<Character>>
}
