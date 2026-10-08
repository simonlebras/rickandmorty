package app.rickandmorty.core.crashlytics.inject

import android.content.Context
import app.rickandmorty.core.base.BuildFlags
import app.rickandmorty.core.crashlytics.CrashlyticsInitializer
import app.rickandmorty.core.metro.AppContext
import app.rickandmorty.core.startup.Initializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.ElementsIntoSet
import dev.zacsweers.metro.Provides

@BindingContainer
@ContributesTo(AppScope::class)
public object CrashlyticsInitializerProvider {
  @Provides
  @ElementsIntoSet
  public fun provideCrashlyticsInitializer(@AppContext context: Context): Set<Initializer> =
    if (!BuildFlags.isDebug) setOf(CrashlyticsInitializer(context)) else emptySet()
}
