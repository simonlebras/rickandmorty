package app.rickandmorty

import android.app.Application
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.inject.ActivityComponent
import app.rickandmorty.inject.ActivityComponentFactoryOwner
import app.rickandmorty.inject.AppComponent
import app.rickandmorty.inject.create
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader

class RamApplication : Application(), ActivityComponentFactoryOwner, SingletonImageLoader.Factory {
  private val appComponent by unsafeLazy { AppComponent::class.create(this) }

  override fun onCreate() {
    super.onCreate()

    appComponent.initializers.forEach { initializer -> initializer.initialize() }
  }

  override fun activityComponentFactory(): ActivityComponent.Factory =
    appComponent.activityComponentFactory

  override fun newImageLoader(context: PlatformContext): ImageLoader = appComponent.imageLoader
}
