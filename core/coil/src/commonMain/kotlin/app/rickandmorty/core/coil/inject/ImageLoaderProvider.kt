package app.rickandmorty.core.coil.inject

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.util.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import io.ktor.client.HttpClient

@ContributesTo(AppScope::class)
public interface ImageLoaderProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideImageLoader(
      context: PlatformContext,
      httpClient: Lazy<HttpClient>,
      logger: Logger? = null,
    ): ImageLoader =
      ImageLoader.Builder(context)
        .components { add(KtorNetworkFetcherFactory(httpClient.value)) }
        .logger(logger)
        .build()
  }
}
