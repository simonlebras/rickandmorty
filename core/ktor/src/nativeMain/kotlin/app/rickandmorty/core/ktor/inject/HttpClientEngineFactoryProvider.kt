package app.rickandmorty.core.ktor.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin

@ContributesTo(AppScope::class)
public interface HttpClientEngineFactoryProvider {
  public companion object {
    @Provides
    public fun provideHttpClientEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig> =
      Darwin
  }
}
