package app.rickandmorty.data.character

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class RemoteCharacter(
  @SerialName("id") val id: Int,
  @SerialName("name") val name: String,
  @SerialName("status") val status: String,
  @SerialName("species") val species: String,
  @SerialName("type") val type: String,
  @SerialName("gender") val gender: String,
  @SerialName("image") val image: String,
)
