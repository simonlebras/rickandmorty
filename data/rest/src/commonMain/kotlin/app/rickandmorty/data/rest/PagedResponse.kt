package app.rickandmorty.data.rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PagedResponse<T>(
  @SerialName("info") val info: Info,
  @SerialName("results") val results: List<T>,
) {
  @Serializable
  public data class Info(@SerialName("count") val count: Int, @SerialName("pages") val pages: Int)
}

public fun PagedResponse.Info.nextPage(page: Int): Int? = (page + 1).takeIf { it <= pages }
