package app.rickandmorty.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import app.rickandmorty.data.database.entity.EpisodeEntity
import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface EpisodeDao {
  @Upsert public suspend fun upsertAll(episodes: List<EpisodeEntity>)

  @Query(
    """
              SELECT * FROM episode_paged_entry
              INNER JOIN episode ON episode.id = episode_paged_entry.episode_id
              WHERE page = :page
              ORDER BY `index` ASC
          """,
  )
  public fun getEpisodePage(page: Int): Flow<Map<EpisodePagedEntryEntity, EpisodeEntity>>
}
