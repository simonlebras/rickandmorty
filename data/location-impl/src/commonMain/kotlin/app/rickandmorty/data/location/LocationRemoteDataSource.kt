package app.rickandmorty.data.location

import app.rickandmorty.data.rest.PagedResponse
import app.rickandmorty.data.rest.inject.RestHttpClient
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@Inject
internal class LocationRemoteDataSource(@RestHttpClient private val httpClient: HttpClient) {
  suspend fun getLocations(page: Int): PagedResponse<RemoteLocation> =
    httpClient.get("location") { parameter("page", page) }.body()
}
