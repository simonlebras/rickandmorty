package app.rickandmorty.ui

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.annotation.OptIn
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsIntent
import androidx.browser.customtabs.CustomTabsServiceConnection
import androidx.browser.customtabs.CustomTabsSession
import androidx.browser.customtabs.ExperimentalInitialNavigationCanLeaveBrowser
import androidx.compose.ui.platform.UriHandler
import androidx.core.net.toUri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner

/**
 * A [UriHandler] that opens uris in a partial Custom Tab when a browser supporting Custom Tabs is
 * available, falling back to a regular VIEW intent otherwise. The browser is asked to forward uris
 * handled by a default native app (e.g. GitHub links when the GitHub app is installed) to that app
 * instead of loading them in the tab; browsers that do not support the forwarding extras simply
 * load the uri in the tab.
 *
 * Register it as an observer of the host activity's lifecycle: the browser's Custom Tabs service is
 * bound while the activity is started so the browser can warm up ahead of the first launch;
 * launches then reuse the warmed-up [CustomTabsSession].
 */
class CustomTabsUriHandler(private val activity: Activity) : UriHandler, DefaultLifecycleObserver {
  private val browserPackage: String? = findCustomTabsBrowser(activity)

  private var connection: CustomTabsServiceConnection? = null
  private var session: CustomTabsSession? = null

  override fun onStart(owner: LifecycleOwner) {
    val browserPackage = browserPackage ?: return

    val connection =
      object : CustomTabsServiceConnection() {
        override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
          client.warmup(0)
          session = client.newSession(null)
        }

        override fun onServiceDisconnected(name: ComponentName) {
          session = null
        }
      }
    this.connection = connection

    CustomTabsClient.bindCustomTabsService(activity, browserPackage, connection)
  }

  override fun onStop(owner: LifecycleOwner) {
    connection?.let(activity::unbindService)
    connection = null
    session = null
  }

  @OptIn(markerClass = [ExperimentalInitialNavigationCanLeaveBrowser::class])
  override fun openUri(uri: String) {
    val parsedUri = uri.toUri()

    if (browserPackage == null) {
      openInBrowser(parsedUri)
      return
    }

    val initialHeight =
      (activity.resources.displayMetrics.heightPixels * PARTIAL_TAB_HEIGHT_RATIO).toInt()
    val customTabsIntent =
      CustomTabsIntent.Builder(session)
        .setShowTitle(true)
        .setInitialActivityHeightPx(initialHeight, CustomTabsIntent.ACTIVITY_HEIGHT_ADJUSTABLE)
        .setToolbarCornerRadiusDp(PARTIAL_TAB_CORNER_RADIUS_DP)
        .setInitialNavigationAllowedToLeaveBrowser(true)
        .setSendToExternalDefaultHandlerEnabled(true)
        .build()

    // The builder only targets the session's browser once the service is connected; explicitly
    // targeting the Custom Tabs browser keeps launches out of non-supporting default browsers.
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
    const val PARTIAL_TAB_HEIGHT_RATIO = 0.85f
    const val PARTIAL_TAB_CORNER_RADIUS_DP = 16

    /** https://developer.chrome.com/docs/android/custom-tabs/howto-custom-tab-check */
    fun findCustomTabsBrowser(context: Context): String? {
      // All apps that can handle VIEW intents for web pages.
      val activityIntent = Intent(Intent.ACTION_VIEW, "http://www.google.com".toUri())
      val packageNames =
        context.packageManager
          .queryIntentActivities(activityIntent, PackageManager.MATCH_ALL)
          .map { resolveInfo ->
            resolveInfo.activityInfo.packageName
          }

      // The Custom Tabs provider among them, preferring the user's default browser (shared
      // cookies and sign-in state) when it supports Custom Tabs; getPackageName prepends the
      // default handler to the candidates, so other capable browsers are still found otherwise.
      return CustomTabsClient.getPackageName(context, packageNames, /* ignoreDefault= */ false)
    }
  }
}
