package app.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity

@Dao
public interface CharacterPagedEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertAll(pagedEntries: List<CharacterPagedEntryEntity>)

    @Query("SELECT page from character_paged_entry where character_id = :characterId")
    public suspend fun getPage(characterId: String): Int?

    @Query("DELETE from character_paged_entry")
    public suspend fun deleteAll()
}
