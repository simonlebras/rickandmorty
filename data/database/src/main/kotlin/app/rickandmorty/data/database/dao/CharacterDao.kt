package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.rickandmorty.data.database.entity.CharacterEntity

@Dao
public interface CharacterDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(characters: List<CharacterEntity>)

  @Transaction
  @Query(
    """
            SELECT character.* FROM character
            INNER JOIN character_paged_entry ON character_paged_entry.character_id = character.id
            ORDER BY page ASC, `index` ASC
        """
  )
  public fun getPagedCharacters(): PagingSource<Int, CharacterEntity>
}
