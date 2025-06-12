package app.rickandmorty.core.designsystem.theme

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation3.ui.LocalNavAnimatedContentScope

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
public fun SharedElementContextPreview(content: @Composable () -> Unit) {
  RamTheme {
    AnimatedContent(targetState = Unit) {
      CompositionLocalProvider(LocalNavAnimatedContentScope provides this) { content() }
    }
  }
}
