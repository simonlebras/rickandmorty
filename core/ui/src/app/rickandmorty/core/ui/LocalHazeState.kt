package app.rickandmorty.core.ui

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf
import dev.chrisbanes.haze.HazeState

@Suppress("ComposeCompositionLocalUsage")
public val LocalHazeState: ProvidableCompositionLocal<HazeState> = staticCompositionLocalOf {
  error("HazeState not provided")
}
