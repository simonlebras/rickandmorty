package app.rickandmorty.core.coil.inject

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.util.Logger
import io.ktor.client.HttpClient
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@ContributesTo(AppScope::class)
public interface ImageLoaderComponent {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideImageLoader(
        context: PlatformContext,
        httpClient: () -> HttpClient,
        logger: Logger? = null,
    ): ImageLoader = ImageLoader.Builder(context)
        .components {
            add(KtorNetworkFetcherFactory(httpClient))
        }
        .logger(logger)
        .build()
}
