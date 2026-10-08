package app.rickandmorty.data.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PagedResponse<out T>(val info: Info, val results: List<T>) {
  @Serializable
  public data class Info(val count: Int, val next: String?) {
    /** The page number of [next], extracted from its `page` query parameter. */
    val nextPage: Int?
      get() =
        next
          ?.substringAfter("page=", missingDelimiterValue = "")
          ?.substringBefore('&')
          ?.toIntOrNull()
  }
}

@Serializable
public data class CharacterResponse(
  val id: Int,
  val name: String,
  val status: String,
  val species: String,
  val type: String,
  val gender: String,
  val image: String,
)

@Serializable
public data class EpisodeResponse(
  val id: Int,
  val name: String,
  @SerialName("air_date") val airDate: String,
  val episode: String,
)

@Serializable
public data class LocationResponse(
  val id: Int,
  val name: String,
  val type: String,
  val dimension: String,
)
