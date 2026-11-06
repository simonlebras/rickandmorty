package app.rickandmorty.data.rest.inject

import app.rickandmorty.core.ktor.inject.BaseHttpClient
import app.rickandmorty.data.rest.RestConfig
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlin.time.Duration.Companion.seconds
import kotlinx.serialization.json.Json

private val TIMEOUT = 30.seconds
private const val MAX_RETRIES = 2

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
    install(HttpTimeout) {
      requestTimeoutMillis = TIMEOUT.inWholeMilliseconds
      socketTimeoutMillis = TIMEOUT.inWholeMilliseconds
    }
    install(HttpRequestRetry) {
      retryIf(maxRetries = MAX_RETRIES) { _, response ->
        response.status.value in 500..599 || response.status == HttpStatusCode.TooManyRequests
      }
      retryOnException(maxRetries = MAX_RETRIES, retryOnTimeout = true)
      exponentialDelay()
    }
    defaultRequest { url(config.baseUrl) }
  }
}
