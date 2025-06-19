package app.rickandmorty.core.ui.tooling.preview

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler

@OptIn(ExperimentalCoilApi::class)
@Composable
public fun ProvideColorImagePreviewHandler(
  color: Color = MaterialTheme.colorScheme.primary,
  content: @Composable () -> Unit,
) {
  val previewHandler = AsyncImagePreviewHandler { _ -> ColorImage(color = color.toArgb()) }
  CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) { content() }
}
