package app.rickandmorty.data.database.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity
import kotlinx.coroutines.flow.Flow

@Dao
public interface LocationDao {
  @Upsert public suspend fun upsertAll(locations: List<LocationEntity>)

  @Query(
    """
              SELECT * FROM location_paged_entry
              INNER JOIN location ON location.id = location_paged_entry.location_id
              WHERE page = :page
              ORDER BY `index` ASC
          """,
  )
  public fun getLocationPage(page: Int): Flow<Map<LocationPagedEntryEntity, LocationEntity>>
}
