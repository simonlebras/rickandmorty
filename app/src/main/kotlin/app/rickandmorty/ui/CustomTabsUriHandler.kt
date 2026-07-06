package app.rickandmorty.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.annotation.ColorInt
import androidx.annotation.OptIn
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.ExperimentalInitialNavigationCanLeaveBrowser
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.UriHandler
import androidx.core.net.toUri
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

@Composable
fun rememberCustomTabsUriHandler(): UriHandler {
  val activity = checkNotNull(LocalActivity.current) { "No activity to launch Custom Tabs from" }

  val colorScheme = MaterialTheme.colorScheme
  val appearance =
    remember(colorScheme) {
      CustomTabsAppearance(
        toolbarColor = colorScheme.background.toArgb(),
        navigationBarColor = colorScheme.background.toArgb(),
        navigationBarDividerColor = colorScheme.outlineVariant.toArgb(),
        isDark = colorScheme.background.luminance() < 0.5f,
      )
    }

  val uriHandler =
    remember(activity) { CustomTabsUriHandler(activity = activity, appearance = appearance) }

  SideEffect { uriHandler.appearance = appearance }
  LifecycleEventEffect(event = Lifecycle.Event.ON_START) { uriHandler.warmUpBrowser() }

  return uriHandler
}

private data class CustomTabsAppearance(
  @param:ColorInt val toolbarColor: Int,
  @param:ColorInt val navigationBarColor: Int,
  @param:ColorInt val navigationBarDividerColor: Int,
  val isDark: Boolean,
)

/**
 * A [UriHandler] opening uris in a Custom Tab, falling back to a VIEW intent when no installed
 * browser supports them. Uris a native app handles by default (e.g. GitHub links) are forwarded to
 * that app instead of loading in the tab, on browsers supporting the extras.
 */
private class CustomTabsUriHandler(
  private val activity: Activity,
  var appearance: CustomTabsAppearance,
) : UriHandler {
  private val browserPackage = findCustomTabsBrowser(activity)

  fun warmUpBrowser() {
    val browserPackage = browserPackage ?: return
    CustomTabsClient.connectAndInitialize(activity, browserPackage)
  }

  @OptIn(markerClass = [ExperimentalInitialNavigationCanLeaveBrowser::class])
  override fun openUri(uri: String) {
    val parsedUri = uri.toUri()

    if (browserPackage == null) {
      openInBrowser(parsedUri)
      return
    }

    val customTabsIntent =
      CustomTabsIntent.Builder()
        .setShowTitle(true)
        .setInitialNavigationAllowedToLeaveBrowser(true)
        .setSendToExternalDefaultHandlerEnabled(true)
        .setBookmarksButtonEnabled(false)
        .setDownloadButtonEnabled(false)
        .setColorScheme(
          if (appearance.isDark) {
            CustomTabsIntent.COLOR_SCHEME_DARK
          } else {
            CustomTabsIntent.COLOR_SCHEME_LIGHT
          }
        )
        .setDefaultColorSchemeParams(
          CustomTabColorSchemeParams.Builder()
            .setToolbarColor(appearance.toolbarColor)
            .setNavigationBarColor(appearance.navigationBarColor)
            .setNavigationBarDividerColor(appearance.navigationBarDividerColor)
            .build()
        )
        .build()

    customTabsIntent.intent.setPackage(browserPackage)

    try {
      customTabsIntent.launchUrl(activity, parsedUri)
    } catch (_: ActivityNotFoundException) {
      openInBrowser(parsedUri)
    }
  }

  private fun openInBrowser(uri: Uri) {
    try {
      activity.startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (_: ActivityNotFoundException) {
      // No installed app can handle the uri.
    }
  }

  private companion object {
    /** https://developer.chrome.com/docs/android/custom-tabs/howto-custom-tab-check */
    fun findCustomTabsBrowser(context: Context): String? {
      val activityIntent =
        Intent(Intent.ACTION_VIEW, "https://".toUri()).addCategory(Intent.CATEGORY_BROWSABLE)
      val packageManager = context.packageManager

      // MATCH_ALL suppresses the default-category filtering instead of adding to it, so neither
      // flag enumerates every browser on its own.
      val packageNames =
        listOf(PackageManager.MATCH_DEFAULT_ONLY, PackageManager.MATCH_ALL).flatMapTo(
          LinkedHashSet()
        ) { flags ->
          packageManager.queryIntentActivities(activityIntent, flags).map { resolveInfo ->
            resolveInfo.activityInfo.packageName
          }
        }

      return CustomTabsClient.getPackageName(
        context,
        packageNames.toList(),
        /* ignoreDefault= */ false,
      )
    }
  }
}
