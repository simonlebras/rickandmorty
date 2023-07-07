package app.rickandmorty

import android.app.Application
import android.content.res.Configuration
import app.rickandmorty.core.AppDelegate
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RamApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoaderFactory: ImageLoaderFactory

    @Inject
    lateinit var appDelegate: AppDelegate

    override fun newImageLoader() = imageLoaderFactory.newImageLoader()

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        appDelegate.onConfigurationChanged(newConfig)
    }
}
