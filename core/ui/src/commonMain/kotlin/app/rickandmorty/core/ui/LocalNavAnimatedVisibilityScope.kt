package app.rickandmorty.core.ui

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

public val LocalNavAnimatedVisibilityScope: ProvidableCompositionLocal<AnimatedVisibilityScope> =
  staticCompositionLocalOf {
    error("NavAnimatedVisibilityScope not provided")
  }
