package app.rickandmorty.core.coil.inject

import coil3.PlatformContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object PlatformContextProvider {
  @Provides public fun providePlatformContext(): PlatformContext = PlatformContext.INSTANCE
}
