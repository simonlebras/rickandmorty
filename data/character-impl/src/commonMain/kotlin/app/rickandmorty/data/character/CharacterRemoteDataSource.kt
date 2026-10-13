package app.rickandmorty.data.character

import app.rickandmorty.data.rest.PagedResponse
import app.rickandmorty.data.rest.inject.RestHttpClient
import dev.zacsweers.metro.Inject
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

@Inject
internal class CharacterRemoteDataSource(@RestHttpClient private val httpClient: HttpClient) {
  suspend fun getCharacters(page: Int): PagedResponse<RemoteCharacter> =
    httpClient.get("character") { parameter("page", page) }.body()
}
