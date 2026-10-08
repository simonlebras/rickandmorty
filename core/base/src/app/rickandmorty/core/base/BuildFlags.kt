package app.rickandmorty.core.base

import kotlin.jvm.JvmStatic

public object BuildFlags {
  /**
   * Whether the app runs as a debug build.
   *
   * Release builds assume this is `false` (see `app/proguard-rules.pro`), so R8 strips the code it
   * guards along with everything only reachable from it.
   */
  @JvmStatic
  public var isDebug: Boolean = false
    internal set
}
