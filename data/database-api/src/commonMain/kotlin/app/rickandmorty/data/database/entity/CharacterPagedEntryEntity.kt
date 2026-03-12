package app.rickandmorty.data.database.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import app.rickandmorty.core.paging.PagedEntry

@Entity(
  tableName = "character_paged_entry",
  indices = [Index(value = ["character_id"])],
  primaryKeys = ["page", "index"],
  foreignKeys =
    [
      ForeignKey(
        entity = CharacterEntity::class,
        parentColumns = ["id"],
        childColumns = ["character_id"],
        onDelete = ForeignKey.CASCADE,
      )
    ],
)
public data class CharacterPagedEntryEntity(
  @ColumnInfo(name = "page") override val page: Int,
  @ColumnInfo(name = "next_page") override val nextPage: Int?,
  @ColumnInfo(name = "index") val index: Int,
  @ColumnInfo(name = "character_id") val characterId: String,
) : PagedEntry
