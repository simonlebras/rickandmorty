package app.rickandmorty.core.rootcontent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

@Stable
public fun interface RootContent {
  @Composable public fun Content(content: @Composable () -> Unit)
}
