package app.rickandmorty.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import app.rickandmorty.data.model.Episode

@Entity(tableName = "episode")
public data class EpisodeEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "air_date") val airDate: String,
    @ColumnInfo(name = "episode") val episode: String,
)

public fun EpisodeEntity.toEpisode(): Episode = Episode(
    id = id,
    name = name,
    airDate = airDate,
    episode = episode,
)
