package app.rickandmorty.core.ktor

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.okhttp.OkHttp
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface HttpClientEngineFactoryComponent {
    @Provides
    public fun provideHttpClientEngineFactory(): HttpClientEngineFactory<HttpClientEngineConfig> = OkHttp
}
