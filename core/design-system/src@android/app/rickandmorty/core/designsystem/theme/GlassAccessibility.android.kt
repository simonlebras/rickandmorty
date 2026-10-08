package app.rickandmorty.core.designsystem.theme

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.view.accessibility.AccessibilityManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.getSystemService
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.glass.GlassAccessibilitySettings

private const val MEDIUM_CONTRAST = 0.5f

@OptIn(ExperimentalHazeApi::class)
@Composable
internal actual fun rememberGlassAccessibilitySettings(): GlassAccessibilitySettings {
  val context = LocalContext.current
  var settings by
    remember(context) {
      mutableStateOf(context.currentGlassAccessibilitySettings())
    }

  DisposableEffect(context) {
    val executor = context.mainExecutor
    val unregisters = mutableListOf<() -> Unit>()

    if (Build.VERSION.SDK_INT >= 34) {
      val uiModeManager = context.getSystemService<UiModeManager>()!!
      val listener = UiModeManager.ContrastChangeListener {
        settings = context.currentGlassAccessibilitySettings()
      }
      uiModeManager.addContrastChangeListener(executor, listener)
      unregisters += { uiModeManager.removeContrastChangeListener(listener) }
    }

    if (Build.VERSION.SDK_INT >= 36) {
      val accessibilityManager = context.getSystemService<AccessibilityManager>()!!
      val listener = AccessibilityManager.HighContrastTextStateChangeListener {
        settings = context.currentGlassAccessibilitySettings()
      }
      accessibilityManager.addHighContrastTextStateChangeListener(executor, listener)
      unregisters += {
        accessibilityManager.removeHighContrastTextStateChangeListener(listener)
      }
    }

    onDispose { unregisters.forEach { it() } }
  }

  return settings
}

@OptIn(ExperimentalHazeApi::class)
private fun Context.currentGlassAccessibilitySettings(): GlassAccessibilitySettings {
  return GlassAccessibilitySettings(increaseContrast = isIncreaseContrastEnabled())
}

private fun Context.isIncreaseContrastEnabled(): Boolean {
  val contrast =
    Build.VERSION.SDK_INT >= 34 && getSystemService<UiModeManager>()!!.contrast >= MEDIUM_CONTRAST
  val highContrastText =
    Build.VERSION.SDK_INT >= 36 &&
      getSystemService<AccessibilityManager>()!!.isHighContrastTextEnabled
  return contrast || highContrastText
}
