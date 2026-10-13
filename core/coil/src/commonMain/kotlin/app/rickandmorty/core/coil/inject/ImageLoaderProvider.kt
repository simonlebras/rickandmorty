package app.rickandmorty.core.coil.inject

import app.rickandmorty.core.ktor.inject.BaseHttpClient
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.annotation.ExperimentalCoilApi
import coil3.network.DeDupeConcurrentRequestStrategy
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.util.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

@BindingContainer
@ContributesTo(AppScope::class)
public object ImageLoaderProvider {
  @OptIn(ExperimentalCoilApi::class)
  @Provides
  @SingleIn(AppScope::class)
  public fun provideImageLoader(
    context: PlatformContext,
    @BaseHttpClient httpClient: HttpClient,
    logger: Logger? = null,
  ): ImageLoader =
    ImageLoader.Builder(context)
      .components {
        add(
          KtorNetworkFetcherFactory(
            httpClient = httpClient,
            concurrentRequestStrategy = DeDupeConcurrentRequestStrategy(),
          ),
        )
      }
      .logger(logger)
      .build()
}
