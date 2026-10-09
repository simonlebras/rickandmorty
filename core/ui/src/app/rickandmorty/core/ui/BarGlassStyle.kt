package app.rickandmorty.core.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.glass.GlassStyle

@OptIn(ExperimentalHazeApi::class)
internal val BarGlassStyle = GlassStyle {
  shape(RoundedCornerShape(0.dp))
  edgeSoftness(0.dp)
}
