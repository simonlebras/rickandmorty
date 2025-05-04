package app.rickandmorty.inject

import android.app.Activity
import dev.zacsweers.metro.Provider
import kotlin.reflect.KClass

typealias ActivityProviders = Map<KClass<out Activity>, Provider<Activity>>

interface ActivityProvidersOwner {
  val activityProviders: ActivityProviders
}
