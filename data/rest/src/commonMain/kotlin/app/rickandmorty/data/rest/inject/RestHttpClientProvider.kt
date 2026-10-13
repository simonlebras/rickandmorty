package app.rickandmorty.data.rest.inject

import app.rickandmorty.core.ktor.inject.BaseHttpClient
import app.rickandmorty.data.rest.RestConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

@BindingContainer
@ContributesTo(AppScope::class)
public object RestHttpClientProvider {
  @Provides
  @SingleIn(AppScope::class)
  @RestHttpClient
  public fun provideRestHttpClient(
    @BaseHttpClient httpClient: HttpClient,
    json: Json,
    config: RestConfig,
  ): HttpClient = httpClient.config {
    expectSuccess = true
    install(ContentNegotiation) { json(json) }
    defaultRequest { url(config.baseUrl) }
  }
}
