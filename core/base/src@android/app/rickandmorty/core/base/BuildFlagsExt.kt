package app.rickandmorty.core.base

import android.content.Context
import android.content.pm.ApplicationInfo

public fun BuildFlags.init(context: Context) {
  isDebug = context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
}
