package app.rickandmorty.core.coil

import android.app.ActivityManager
import android.content.Context
import androidx.core.content.getSystemService
import coil3.ImageLoader
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import coil3.request.allowRgb565
import coil3.util.Logger
import dagger.BindsOptionalOf
import dagger.Lazy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        fun provideImageLoader(
            @ApplicationContext context: Context,
            okHttpClient: Lazy<OkHttpClient>,
            logger: Optional<Logger>,
        ): ImageLoader {
            return ImageLoader.Builder(context)
                .components {
                    add(
                        OkHttpNetworkFetcherFactory(
                            httpClient = lazy { okHttpClient.get() },
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
