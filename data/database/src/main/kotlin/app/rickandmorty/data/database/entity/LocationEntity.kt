package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.rickandmorty.data.model.Location

@Entity(tableName = "location")
public data class LocationEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "type") val type: String,
    @ColumnInfo(name = "dimension") val dimension: String,
)

public fun LocationEntity.toLocation(): Location = Location(
    id = id,
    name = name,
    type = type,
    dimension = dimension,
)
