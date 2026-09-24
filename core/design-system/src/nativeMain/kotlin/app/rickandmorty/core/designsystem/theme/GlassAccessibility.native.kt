package app.rickandmorty.core.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.glass.GlassAccessibilitySettings
import platform.Foundation.NSNotificationCenter
import platform.Foundation.NSOperationQueue
import platform.UIKit.UIAccessibilityButtonShapesEnabled
import platform.UIKit.UIAccessibilityButtonShapesEnabledStatusDidChangeNotification
import platform.UIKit.UIAccessibilityDarkerSystemColorsEnabled
import platform.UIKit.UIAccessibilityDarkerSystemColorsStatusDidChangeNotification
import platform.UIKit.UIAccessibilityIsReduceTransparencyEnabled
import platform.UIKit.UIAccessibilityReduceTransparencyStatusDidChangeNotification

@OptIn(ExperimentalHazeApi::class)
@Composable
internal actual fun rememberGlassAccessibilitySettings(): GlassAccessibilitySettings {
  var settings by remember { mutableStateOf(currentGlassAccessibilitySettings()) }

  DisposableEffect(Unit) {
    val notificationCenter = NSNotificationCenter.defaultCenter
    val observers =
      listOf(
          UIAccessibilityButtonShapesEnabledStatusDidChangeNotification,
          UIAccessibilityDarkerSystemColorsStatusDidChangeNotification,
          UIAccessibilityReduceTransparencyStatusDidChangeNotification,
        )
        .map { name ->
          notificationCenter.addObserverForName(name, null, NSOperationQueue.mainQueue) {
            settings = currentGlassAccessibilitySettings()
          }
        }

    onDispose { observers.forEach(notificationCenter::removeObserver) }
  }

  return settings
}

@OptIn(ExperimentalHazeApi::class)
private fun currentGlassAccessibilitySettings(): GlassAccessibilitySettings =
  GlassAccessibilitySettings(
    reduceTransparency = UIAccessibilityIsReduceTransparencyEnabled(),
    increaseContrast = UIAccessibilityDarkerSystemColorsEnabled(),
    showBorders = UIAccessibilityButtonShapesEnabled(),
  )
