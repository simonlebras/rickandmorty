package app.rickandmorty.core.coil.inject

import android.content.Context
import app.rickandmorty.core.injectannotations.AppContext
import coil3.PlatformContext
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface PlatformContextComponent {
    @Provides
    public fun providePlatformContext(
        context: @AppContext Context,
    ): PlatformContext = context
}
