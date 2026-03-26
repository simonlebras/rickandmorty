package app.rickandmorty.core.designsystem.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.tooling.preview.PreviewWrapper
import androidx.navigation3.ui.LocalNavAnimatedContentScope

public class SharedElementPreviewWrapper : PreviewWrapper {
  @Suppress("ComposeUnstableReceiver")
  @Composable
  override fun Wrap(content: @Composable () -> Unit) {
    RamTheme {
      @Suppress("UnusedContentLambdaTargetStateParameter")
      AnimatedContent(targetState = Unit) {
        CompositionLocalProvider(LocalNavAnimatedContentScope provides this) { content() }
      }
    }
  }
}
