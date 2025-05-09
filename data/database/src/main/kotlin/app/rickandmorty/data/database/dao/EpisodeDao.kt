package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import app.rickandmorty.data.database.entity.EpisodeEntity

// @Dao
public interface EpisodeDao {
  // @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(episodes: List<EpisodeEntity>)

  //  @Transaction
  //  @Query(
  //    """
  //            SELECT episode.* FROM episode
  //            INNER JOIN episode_paged_entry ON episode_paged_entry.episode_id = episode.id
  //            ORDER BY page ASC, `index` ASC
  //        """
  //  )
  public fun getPagedEpisodes(): PagingSource<Int, EpisodeEntity>
}
