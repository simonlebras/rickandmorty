package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import app.rickandmorty.data.paging.PagedEntry

@Entity(
  tableName = "episode_paged_entry",
  indices = [Index(value = ["episode_id"])],
  primaryKeys = ["page", "index"],
  foreignKeys =
    [
      ForeignKey(
        entity = EpisodeEntity::class,
        parentColumns = ["id"],
        childColumns = ["episode_id"],
        onDelete = ForeignKey.CASCADE,
      )
    ],
)
public data class EpisodePagedEntryEntity(
  @ColumnInfo(name = "page") override val page: Int,
  @ColumnInfo(name = "next_page") override val nextPage: Int?,
  @ColumnInfo(name = "index") val index: Int,
  @ColumnInfo(name = "episode_id") val episodeId: String,
) : PagedEntry
