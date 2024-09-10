package app.rickandmorty

import android.app.Application
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.startup.Initializer
import app.rickandmorty.di.AppComponent
import app.rickandmorty.di.create
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.Lazy
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RamApplication :
    Application(),
    SingletonImageLoader.Factory {
    private val appComponent by unsafeLazy {
        AppComponent.create(this)
    }

    @Inject
    lateinit var imageLoader: Lazy<ImageLoader>

    @Inject
    lateinit var initializers: Set<@JvmSuppressWildcards Initializer>

    override fun onCreate() {
        super.onCreate()

        initializers.forEach { initializer ->
            initializer.initialize()
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader.get()
}
