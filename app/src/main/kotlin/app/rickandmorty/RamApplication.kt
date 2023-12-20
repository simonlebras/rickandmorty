package app.rickandmorty

import android.app.Application
import android.content.res.Configuration
import app.rickandmorty.core.AppDelegate
import app.rickandmorty.coroutines.DefaultDispatcher
import app.rickandmorty.startup.Initializer
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class RamApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoaderFactory: ImageLoaderFactory

    @Inject
    lateinit var appDelegate: AppDelegate

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

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        appDelegate.onConfigurationChanged(newConfig)
    }

    override fun newImageLoader() = imageLoaderFactory.newImageLoader()
}
