package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.rickandmorty.data.model.Character

@Entity(tableName = "character")
public data class CharacterEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "image_url") val imageUrl: String,
)

public fun CharacterEntity.toCharacter(): Character = Character(
    id = id,
    name = name,
    imageUrl = imageUrl,
)
