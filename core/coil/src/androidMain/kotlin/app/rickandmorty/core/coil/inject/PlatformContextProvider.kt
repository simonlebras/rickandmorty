package app.rickandmorty.core.coil.inject

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import coil3.PlatformContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
public interface PlatformContextProvider {
  public companion object {
    @Provides
    public fun providePlatformContext(@AppContext context: Context): PlatformContext = context
  }
}
