package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import app.rickandmorty.data.database.entity.LocationEntity

@Dao
public interface LocationDao {
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public suspend fun insertAll(locations: List<LocationEntity>)

  @Transaction
  @Query(
    """
              SELECT location.* FROM location
              INNER JOIN location_paged_entry ON location_paged_entry.location_id = location.id
              ORDER BY page ASC, `index` ASC
          """
  )
  public fun getPagedLocations(): PagingSource<Int, LocationEntity>
}
