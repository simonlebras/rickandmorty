package app.rickandmorty.core.coil

import android.app.ActivityManager
import android.content.Context
import androidx.core.content.getSystemService
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.util.Logger
import dagger.BindsOptionalOf
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.Optional
import kotlin.jvm.optionals.getOrNull
import okhttp3.Dispatcher
import okhttp3.OkHttpClient

@Module
@InstallIn(SingletonComponent::class)
internal interface ImageLoaderFactoryModule {
    @BindsOptionalOf
    fun bindLogger(): Logger

    companion object {
        @Provides
        fun provideImageLoaderFactory(
            @ApplicationContext context: Context,
            okHttpClient: Lazy<OkHttpClient>,
            logger: Optional<Logger>,
        ): ImageLoaderFactory = ImageLoaderFactory {
            ImageLoader.Builder(context)
                .okHttpClient {
                    val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }
                    okHttpClient.get()
                        .newBuilder()
                        .dispatcher(dispatcher)
                        .build()
                }
                .apply {
                    val activityManager = context.getSystemService<ActivityManager>()!!
                    allowRgb565(activityManager.isLowRamDevice)
                }
                .respectCacheHeaders(false)
                .logger(logger.getOrNull())
                .build()
        }
    }
}
