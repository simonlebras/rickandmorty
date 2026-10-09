package app.rickandmorty.core.rootcontent

import androidx.compose.runtime.Composable
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class)
public class DefaultRootContent : RootContent {
  @Composable
  override fun Content(content: @Composable () -> Unit) {
    content()
  }
}
