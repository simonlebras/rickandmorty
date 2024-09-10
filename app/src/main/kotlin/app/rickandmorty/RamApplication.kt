package app.rickandmorty

import android.app.Application
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.inject.AppComponent
import app.rickandmorty.inject.create
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RamApplication :
    Application(),
    SingletonImageLoader.Factory {
    private val appComponent by unsafeLazy {
        AppComponent.create(this)
    }

    override fun onCreate() {
        super.onCreate()

        appComponent.initializers.forEach { initializer ->
            initializer.initialize()
        }
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader = appComponent.imageLoader
}
