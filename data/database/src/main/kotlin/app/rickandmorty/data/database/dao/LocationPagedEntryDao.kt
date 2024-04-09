package app.rickandmorty.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity

@Dao
public interface LocationPagedEntryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public suspend fun insertAll(pagedEntries: List<LocationPagedEntryEntity>)

    @Query("SELECT * FROM location_paged_entry WHERE location_id = :locationId")
    public suspend fun getPagedEntry(locationId: String): LocationPagedEntryEntity?

    @Query("DELETE FROM location_paged_entry")
    public suspend fun deleteAll()
}
