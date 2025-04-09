package app.rickandmorty.core.designsystem.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.eygraber.compose.placeholder.PlaceholderDefaults
import com.eygraber.compose.placeholder.PlaceholderHighlight
import com.eygraber.compose.placeholder.material3.color
import com.eygraber.compose.placeholder.material3.shimmer
import com.eygraber.compose.placeholder.placeholder

@Composable
public fun AsyncImage(
  model: Any?,
  contentDescription: String?,
  modifier: Modifier = Modifier,
  alignment: Alignment = Alignment.Center,
  contentScale: ContentScale = ContentScale.Fit,
  alpha: Float = DefaultAlpha,
  colorFilter: ColorFilter? = null,
  filterQuality: FilterQuality = DrawScope.DefaultFilterQuality,
  clipToBounds: Boolean = true,
) {
  var isLoading by remember { mutableStateOf(true) }

  AsyncImage(
    model = model,
    contentDescription = contentDescription,
    modifier =
      modifier then
        if (!LocalInspectionMode.current) {
          Modifier.placeholder(
            visible = isLoading,
            color = PlaceholderDefaults.color(),
            highlight = PlaceholderHighlight.shimmer(),
          )
        } else {
          Modifier
        },
    transform = AsyncImagePainter.DefaultTransform,
    onState = { state -> isLoading = state is AsyncImagePainter.State.Loading },
    alignment = alignment,
    contentScale = contentScale,
    alpha = alpha,
    colorFilter = colorFilter,
    filterQuality = filterQuality,
    clipToBounds = clipToBounds,
  )
}
