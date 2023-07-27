package app.rickandmorty

import android.app.Application
import android.content.res.Configuration
import app.rickandmorty.core.AppDelegate
import app.rickandmorty.hilt.HiltSet
import app.rickandmorty.startup.Initializer
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RamApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoaderFactory: ImageLoaderFactory

    @Inject
    lateinit var appDelegate: AppDelegate

    @Inject
    lateinit var initializers: HiltSet<Initializer>

    override fun onCreate() {
        super.onCreate()

        initializers.forEach(Initializer::initialize)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        appDelegate.onConfigurationChanged(newConfig)
    }

    override fun newImageLoader() = imageLoaderFactory.newImageLoader()
}
