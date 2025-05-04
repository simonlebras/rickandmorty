package app.rickandmorty.core.ktor.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory

@ContributesTo(AppScope::class)
public interface HttpClientProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideHttpClient(
      httpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
    ): HttpClient = HttpClient(httpClientEngineFactory) { engine { pipelining = true } }
  }
}
