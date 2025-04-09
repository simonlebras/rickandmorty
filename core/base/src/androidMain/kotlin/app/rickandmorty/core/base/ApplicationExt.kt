package app.rickandmorty.core.base

import android.app.Activity
import android.app.Application
import android.os.Bundle

public inline fun Application.doOnActivityPreCreated(crossinline block: () -> Unit) {
  registerActivityLifecycleCallbacks(
    object : Application.ActivityLifecycleCallbacks {
      override fun onActivityPreCreated(activity: Activity, savedInstanceState: Bundle?) {
        unregisterActivityLifecycleCallbacks(this)
        block()
      }

      override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) = Unit

      override fun onActivityStarted(activity: Activity) = Unit

      override fun onActivityResumed(activity: Activity) = Unit

      override fun onActivityPaused(activity: Activity) = Unit

      override fun onActivityStopped(activity: Activity) = Unit

      override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) = Unit

      override fun onActivityDestroyed(activity: Activity) = Unit
    }
  )
}
