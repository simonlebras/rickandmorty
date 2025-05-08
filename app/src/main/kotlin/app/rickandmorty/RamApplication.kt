package app.rickandmorty

import android.app.Application
import app.rickandmorty.core.base.unsafeLazy
import app.rickandmorty.core.startup.Initializer
import app.rickandmorty.inject.ActivityProviders
import app.rickandmorty.inject.ActivityProvidersOwner
import app.rickandmorty.inject.AppGraph
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.createGraphFactory

class RamApplication : Application(), ActivityProvidersOwner, SingletonImageLoader.Factory {
  private val appGraph by unsafeLazy { createGraphFactory<AppGraph.Factory>().create(this) }

  @Inject lateinit var imageLoader: ImageLoader

  override val activityProviders: ActivityProviders
    get() = appGraph.activityProviders

  override fun onCreate() {
    super.onCreate()

    appGraph.inject(this)
  }

  @Inject
  fun initialize(initializers: Set<Initializer>) {
    initializers.forEach(Initializer::initialize)
  }

  override fun newImageLoader(context: PlatformContext): ImageLoader = imageLoader
}
