package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "location_paged_entry",
    indices = [Index(value = ["location_id"])],
    primaryKeys = ["page", "index"],
    foreignKeys = [
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
    @ColumnInfo(name = "index") val index: Int,
    @ColumnInfo(name = "location_id") val locationId: String,
)
