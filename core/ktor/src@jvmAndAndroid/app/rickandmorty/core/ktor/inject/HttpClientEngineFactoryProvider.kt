package app.rickandmorty.core.ktor.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp

@BindingContainer
@ContributesTo(AppScope::class)
public object HttpClientEngineFactoryProvider {
  @Provides
  public fun provideHttpClientEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig> =
    OkHttp
}
