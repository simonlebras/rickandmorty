package app.rickandmorty.data.rest

import io.ktor.http.Url
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class PagedResponse<T>(
  @SerialName("info") val info: Info,
  @SerialName("results") val results: List<T>,
) {
  @Serializable
  public data class Info(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
  ) {
    val nextPage: Int?
      get() = next?.let { url -> Url(url).parameters["page"]?.toInt() }
  }
}
