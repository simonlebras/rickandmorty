package app.rickandmorty

import android.app.Application
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class RickandmortyApplication : Application(), ImageLoaderFactory {
    @Inject
    lateinit var imageLoaderFactory: ImageLoaderFactory

    override fun newImageLoader() = imageLoaderFactory.newImageLoader()
}
