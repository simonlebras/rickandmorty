package app.rickandmorty.core.coil.inject

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import coil3.PlatformContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object PlatformContextProvider {
  @Provides
  public fun providePlatformContext(@AppContext context: Context): PlatformContext = context
}
