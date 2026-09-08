package app.rickandmorty.core.ktor.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

@BindingContainer
@ContributesTo(AppScope::class)
public object HttpClientProvider {
  @Provides
  @SingleIn(AppScope::class)
  public fun provideHttpClient(
    httpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
  ): HttpClient = HttpClient(httpClientEngineFactory) { engine { pipelining = true } }
}
