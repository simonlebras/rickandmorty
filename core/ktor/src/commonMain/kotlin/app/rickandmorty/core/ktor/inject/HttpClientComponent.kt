package app.rickandmorty.core.ktor.inject

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
public interface HttpClientComponent {
  @Provides
  @SingleIn(AppScope::class)
  public fun provideHttpClient(
    httpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>
  ): HttpClient = HttpClient(httpClientEngineFactory) { engine { pipelining = true } }
}
