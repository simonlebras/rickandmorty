package app.rickandmorty.data.database.entity

import app.rickandmorty.data.model.Character

// @Entity(tableName = "character")
public data class CharacterEntity(
  // @PrimaryKey @ColumnInfo(name = "id")
  val id: String,
  // @ColumnInfo(name = "name")
  val name: String,
  // @ColumnInfo(name = "status")
  val status: String,
  // @ColumnInfo(name = "species")
  val species: String,
  // @ColumnInfo(name = "type")
  val type: String,
  //  @ColumnInfo(name = "gender")
  val gender: String,
  // @ColumnInfo(name = "image")
  val image: String,
)

public fun CharacterEntity.toCharacter(): Character =
  Character(
    id = id,
    name = name,
    status = Character.Status.from(status),
    species = Character.Species.from(species),
    type = type,
    gender = Character.Gender.from(gender),
    image = image,
  )
