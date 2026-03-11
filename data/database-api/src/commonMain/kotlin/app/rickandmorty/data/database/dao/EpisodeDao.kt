package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import app.rickandmorty.data.database.entity.EpisodeEntity

@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
public interface EpisodeDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(episodes: List<EpisodeEntity>)

  @Transaction
  @Query(
    """
              SELECT episode.* FROM episode
              INNER JOIN episode_paged_entry ON episode_paged_entry.episode_id = episode.id
              ORDER BY page ASC, `index` ASC
          """
  )
  public fun getPagedEpisodes(): PagingSource<Int, EpisodeEntity>
}
