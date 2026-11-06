package app.rickandmorty.data.database.dao

import androidx.room3.Dao
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity

@Dao
public interface CharacterPagedEntryDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(pagedEntries: List<CharacterPagedEntryEntity>)

  @Query("DELETE FROM character_paged_entry WHERE page = :page")
  public suspend fun deletePage(page: Int)

  @Query("DELETE FROM character_paged_entry WHERE page > :lastPage")
  public suspend fun deletePagesAfter(lastPage: Int)

  @Query("DELETE FROM character_paged_entry") public suspend fun deleteAll()
}
