package app.rickandmorty.core.base

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager

public fun Context.setComponentEnabled(componentName: ComponentName, enabled: Boolean) {
  val newState =
    if (enabled) {
      PackageManager.COMPONENT_ENABLED_STATE_ENABLED
    } else {
      PackageManager.COMPONENT_ENABLED_STATE_DISABLED
    }
  packageManager.setComponentEnabledSetting(componentName, newState, PackageManager.DONT_KILL_APP)
}

public fun Context.isComponentEnabled(componentName: ComponentName): Boolean =
  packageManager.getComponentEnabledSetting(componentName) ==
    PackageManager.COMPONENT_ENABLED_STATE_ENABLED
