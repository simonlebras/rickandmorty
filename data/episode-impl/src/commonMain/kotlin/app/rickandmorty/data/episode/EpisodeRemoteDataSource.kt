package app.rickandmorty.data.episode

import app.rickandmorty.data.rest.PagedResponse
import app.rickandmorty.data.rest.inject.RestHttpClient
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@Inject
internal class EpisodeRemoteDataSource(@RestHttpClient private val httpClient: HttpClient) {
  suspend fun getEpisodes(page: Int): PagedResponse<RemoteEpisode> =
    httpClient.get("episode") { parameter("page", page) }.body()
}
