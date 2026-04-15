package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

@Suppress("ComposeCompositionLocalUsage")
internal val LocalScaffoldInsets: ProvidableCompositionLocal<WindowInsets> =
  staticCompositionLocalOf {
    WindowInsets()
  }
