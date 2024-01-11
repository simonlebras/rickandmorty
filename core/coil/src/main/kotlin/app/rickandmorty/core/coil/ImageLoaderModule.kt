package app.rickandmorty.core.coil

import android.app.ActivityManager
import android.content.Context
import androidx.core.content.getSystemService
import coil3.ImageLoader
import coil3.annotation.ExperimentalCoilApi
import coil3.network.NetworkFetcher
import coil3.request.allowRgb565
import coil3.util.Logger
import dagger.BindsOptionalOf
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import java.util.Optional
import javax.inject.Singleton
import kotlin.jvm.optionals.getOrNull
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface ImageLoaderModule {
    @BindsOptionalOf
    fun bindLogger(): Logger

    companion object {
        @Provides
        @Singleton
        fun provideHttpClient(
            okHttpClient: Lazy<OkHttpClient>,
        ): HttpClient {
            return HttpClient(OkHttp) {
                engine {
                    preconfigured = okHttpClient.get()
                }
            }
        }

        @OptIn(ExperimentalCoilApi::class)
        @Provides
        @Singleton
        fun provideImageLoader(
            @ApplicationContext context: Context,
            httpClient: Lazy<HttpClient>,
            logger: Optional<Logger>,
        ): ImageLoader {
            return ImageLoader.Builder(context)
                .components {
                    add(
                        NetworkFetcher.Factory(
                            httpClient = lazy(LazyThreadSafetyMode.NONE) {
                                httpClient.get()
                            },
                        ),
                    )
                }
                .apply {
                    val activityManager = context.getSystemService<ActivityManager>()!!
                    allowRgb565(activityManager.isLowRamDevice)
                }
                .logger(logger.getOrNull())
                .build()
        }
    }
}
