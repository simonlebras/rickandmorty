package app.rickandmorty.data.database.dao

import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity

// @Dao
public interface EpisodePagedEntryDao {
  // @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(pagedEntries: List<EpisodePagedEntryEntity>)

  // @Query("SELECT * FROM episode_paged_entry WHERE episode_id = :episodeId")
  public suspend fun getPagedEntry(episodeId: String): EpisodePagedEntryEntity?

  // @Query("DELETE FROM episode_paged_entry")
  public suspend fun deleteAll()
}
