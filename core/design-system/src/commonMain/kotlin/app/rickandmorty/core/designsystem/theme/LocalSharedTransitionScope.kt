package app.rickandmorty.core.designsystem.theme

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

@OptIn(ExperimentalSharedTransitionApi::class)
@Suppress("ComposeCompositionLocalUsage")
public val LocalSharedTransitionScope: ProvidableCompositionLocal<SharedTransitionScope> =
  staticCompositionLocalOf {
    error("SharedTransitionScope not provided")
  }
