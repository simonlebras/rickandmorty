package app.rickandmorty.data.database.dao

import androidx.paging.PagingSource
import androidx.room3.Dao
import androidx.room3.DaoReturnTypeConverters
import androidx.room3.Insert
import androidx.room3.OnConflictStrategy
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.paging.PagingSourceDaoReturnTypeConverter
import app.rickandmorty.data.database.entity.LocationEntity

@Dao
@DaoReturnTypeConverters(PagingSourceDaoReturnTypeConverter::class)
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
