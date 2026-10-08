package app.rickandmorty.ui.debugdrawer

import androidx.compose.runtime.Composable
import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.rootcontent.DefaultRootContent
import app.rickandmorty.core.rootcontent.RootContent
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

internal class DebugRootContent : RootContent {
  @Composable
  override fun Content(content: @Composable () -> Unit) {
    DebugDrawer(content = content)
  }
}

@BindingContainer
@ContributesTo(AppScope::class, replaces = [DefaultRootContent::class])
public object DebugRootContentProvider {
  @Provides
  public fun provideRootContent(): RootContent =
    if (BuildFlags.isDebug) DebugRootContent() else DefaultRootContent()
}
