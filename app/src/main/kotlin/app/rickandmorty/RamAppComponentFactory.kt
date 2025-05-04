package app.rickandmorty

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.core.app.AppComponentFactory
import app.rickandmorty.inject.ActivityProviders
import app.rickandmorty.inject.ActivityProvidersOwner
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

@Suppress("unused")
class RamAppComponentFactory : AppComponentFactory() {
  override fun instantiateActivityCompat(
    cl: ClassLoader,
    className: String,
    intent: Intent?,
  ): Activity {
    return getInstance(cl, className, activityProviders)
      ?: super.instantiateActivityCompat(cl, className, intent)
  }

  override fun instantiateApplicationCompat(cl: ClassLoader, className: String): Application {
    return super.instantiateApplicationCompat(cl, className).also { application ->
      activityProviders = (application as ActivityProvidersOwner).activityProviders
    }
  }

  private inline fun <reified T : Any> getInstance(
    cl: ClassLoader,
    className: String,
    providers: Map<KClass<out T>, Provider<T>>,
  ): T? {
    val clazz = Class.forName(className, false, cl).asSubclass(T::class.java)
    val modelProvider = providers[clazz.kotlin] ?: return null
    return modelProvider()
  }

  companion object {
    private lateinit var activityProviders: ActivityProviders
  }
}
