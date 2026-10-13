package app.rickandmorty.data.episode

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteEpisode(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("air_date") val airDate: String,
  @SerialName("episode") val episode: String,
)
