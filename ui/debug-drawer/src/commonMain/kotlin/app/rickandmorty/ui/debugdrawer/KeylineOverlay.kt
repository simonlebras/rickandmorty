package app.rickandmorty.ui.debugdrawer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
internal fun KeylineOverlay() {
  Spacer(
    modifier =
      Modifier.fillMaxSize().drawWithCache {
        val gridColor = Color.Magenta.copy(alpha = 0.2f)
        val gridSpacing = 8.dp.toPx()

        val path = Path()

        // Draw vertical lines
        var x = 0f
        while (x <= size.width) {
          path.moveTo(x, 0f)
          path.lineTo(x, size.height)
          x += gridSpacing
        }

        // Draw horizontal lines
        var y = 0f
        while (y <= size.height) {
          path.moveTo(0f, y)
          path.lineTo(size.width, y)
          y += gridSpacing
        }

        onDrawBehind {
          drawPath(path = path, color = gridColor, style = Stroke(width = 1f))
        }
      }
  )
}
