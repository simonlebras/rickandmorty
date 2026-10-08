package app.rickandmorty.data.network

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://rickandmortyapi.com/api"

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
internal class KtorRickAndMortyApi(private val httpClient: HttpClient, private val json: Json) :
  RickAndMortyApi {
  override suspend fun getCharacters(page: Int): PagedResponse<CharacterResponse> =
    getPage(path = "character", page = page, serializer = CharacterResponse.serializer())

  override suspend fun getEpisodes(page: Int): PagedResponse<EpisodeResponse> =
    getPage(path = "episode", page = page, serializer = EpisodeResponse.serializer())

  override suspend fun getLocations(page: Int): PagedResponse<LocationResponse> =
    getPage(path = "location", page = page, serializer = LocationResponse.serializer())

  private suspend fun <T> getPage(
    path: String,
    page: Int,
    serializer: KSerializer<T>,
  ): PagedResponse<T> {
    val response = httpClient.get("$BASE_URL/$path") { parameter("page", page) }
    check(response.status.value in 200..299) { "GET $path?page=$page failed: ${response.status}" }

    return json.decodeFromString(PagedResponse.serializer(serializer), response.bodyAsText())
  }
}
