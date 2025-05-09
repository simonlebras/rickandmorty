package app.rickandmorty.data.database.dao

import app.rickandmorty.data.database.entity.CharacterPagedEntryEntity

// @Dao
public interface CharacterPagedEntryDao {
  // @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(pagedEntries: List<CharacterPagedEntryEntity>)

  // @Query("SELECT * FROM character_paged_entry WHERE character_id = :characterId")
  public suspend fun getPagedEntry(characterId: String): CharacterPagedEntryEntity?

  // @Query("DELETE FROM character_paged_entry")
  public suspend fun deleteAll()
}
