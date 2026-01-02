package app.rickandmorty.core.appinfo

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.core.content.pm.PackageInfoCompat
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
public class AndroidAppInfo(@AppContext context: Context) : AppInfo {
  private val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)

  override val versionName: String = packageInfo.versionName ?: "unknown"
  override val versionCode: Long = PackageInfoCompat.getLongVersionCode(packageInfo)
  override val packageName: String = context.packageName
  override val isDebug: Boolean =
    (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
}
