package app.rickandmorty.core.coil.inject

import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface PlatformContextComponent {
  @Provides
  public fun providePlatformContext(): PlatformContext = PlatformContext.Companion.INSTANCE
}
