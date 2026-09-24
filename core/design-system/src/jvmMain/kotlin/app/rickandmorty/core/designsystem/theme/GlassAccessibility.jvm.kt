package app.rickandmorty.core.designsystem.theme

import androidx.compose.runtime.Composable
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.glass.GlassAccessibilitySettings

@OptIn(ExperimentalHazeApi::class)
@Composable
internal actual fun rememberGlassAccessibilitySettings(): GlassAccessibilitySettings =
  GlassAccessibilitySettings()
