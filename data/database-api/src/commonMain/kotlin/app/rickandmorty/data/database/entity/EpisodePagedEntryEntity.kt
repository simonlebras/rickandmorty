package app.rickandmorty.data.database.entity

import androidx.room3.ColumnInfo
import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index

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
      ),
    ],
)
public data class EpisodePagedEntryEntity(
  @ColumnInfo(name = "page") val page: Int,
  @ColumnInfo(name = "next_page") val nextPage: Int?,
  @ColumnInfo(name = "index") val index: Int,
  @ColumnInfo(name = "episode_id") val episodeId: String,
)
