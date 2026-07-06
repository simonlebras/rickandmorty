package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Composable
import app.rickandmorty.core.rootcontent.DefaultRootContent
import app.rickandmorty.core.rootcontent.RootContent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding

@ContributesBinding(AppScope::class, replaces = [DefaultRootContent::class])
internal class DebugRootContent : RootContent {
  @Composable
  override fun Content(content: @Composable () -> Unit) {
    DebugDrawer(content = content)
  }
}
