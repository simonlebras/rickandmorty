package app.rickandmorty.data.location

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteLocation(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("type") val type: String,
  @SerialName("dimension") val dimension: String,
)
