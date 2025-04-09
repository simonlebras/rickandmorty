package app.rickandmorty.core.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import app.rickandmorty.core.designsystem.theme.RamTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
public fun PreviewWrapper(content: @Composable () -> Unit) {
  RamTheme {
    SharedTransitionLayout {
      AnimatedVisibility(visible = true) {
        CompositionLocalProvider(
          LocalSharedTransitionScope provides this@SharedTransitionLayout,
          LocalNavAnimatedVisibilityScope provides this,
          content = content,
        )
      }
    }
  }
}
