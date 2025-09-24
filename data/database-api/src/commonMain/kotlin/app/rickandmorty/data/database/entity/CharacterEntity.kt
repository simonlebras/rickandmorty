package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
public data class CharacterEntity(
  @PrimaryKey @ColumnInfo(name = "id") val id: String,
  @ColumnInfo(name = "name") val name: String,
  @ColumnInfo(name = "status") val status: String,
  @ColumnInfo(name = "species") val species: String,
  @ColumnInfo(name = "type") val type: String,
  @ColumnInfo(name = "gender") val gender: String,
  @ColumnInfo(name = "image") val image: String,
)
