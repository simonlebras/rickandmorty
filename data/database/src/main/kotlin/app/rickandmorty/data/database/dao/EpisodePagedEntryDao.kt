package app.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity

@Dao
public interface EpisodePagedEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertAll(pagedEntries: List<EpisodePagedEntryEntity>)

    @Query("SELECT page from episode_paged_entry where episode_id = :episodeId")
    public suspend fun getPage(episodeId: String): Int?

    @Query("DELETE from episode_paged_entry")
    public suspend fun deleteAll()
}
