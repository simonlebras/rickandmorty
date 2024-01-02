package app.rickandmorty

import android.app.Application
import app.rickandmorty.core.coroutines.DefaultDispatcher
import app.rickandmorty.core.startup.Initializer
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class RamApplication : Application(), SingletonImageLoader.Factory {
    @Inject
    lateinit var imageLoader: Lazy<ImageLoader>

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards Initializer>

    @Inject
    @DefaultDispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    override fun onCreate() {
        super.onCreate()

        runBlocking {
            initializers
                .map { initializer ->
                    async(defaultDispatcher) {
                        initializer.initialize()
                    }
                }
                .awaitAll()
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return imageLoader.get()
    }
}
