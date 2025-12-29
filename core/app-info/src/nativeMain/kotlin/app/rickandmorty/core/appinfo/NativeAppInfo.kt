package app.rickandmorty.core.appinfo

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlin.experimental.ExperimentalNativeApi
import platform.Foundation.NSBundle

@ContributesBinding(AppScope::class)
public class NativeAppInfo : AppInfo {
  override val versionName: String =
    NSBundle.mainBundle.infoDictionary?.get("CFBundleShortVersionString") as? String ?: "unknown"

  override val versionCode: Long =
    (NSBundle.mainBundle.infoDictionary?.get("CFBundleVersion") as? String)?.toLongOrNull() ?: 0L

  override val packageName: String = NSBundle.mainBundle.bundleIdentifier ?: "unknown"

  @OptIn(ExperimentalNativeApi::class) override val isDebug: Boolean = Platform.isDebugBinary
}
