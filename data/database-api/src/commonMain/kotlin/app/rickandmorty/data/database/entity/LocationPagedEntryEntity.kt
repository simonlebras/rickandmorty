package app.rickandmorty.data.database.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index

@Entity(
  tableName = "location_paged_entry",
  indices = [Index(value = ["location_id"])],
  primaryKeys = ["page", "index"],
  foreignKeys =
    [
      ForeignKey(
        entity = LocationEntity::class,
        parentColumns = ["id"],
        childColumns = ["location_id"],
        onDelete = ForeignKey.CASCADE,
      ),
    ],
)
public data class LocationPagedEntryEntity(
  @ColumnInfo(name = "page") val page: Int,
  @ColumnInfo(name = "next_page") val nextPage: Int?,
  @ColumnInfo(name = "index") val index: Int,
  @ColumnInfo(name = "location_id") val locationId: String,
)
