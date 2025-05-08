package app.rickandmorty.core.coroutines.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

@ContributesTo(AppScope::class)
public interface CoroutineScopesProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    @ApplicationScope
    public fun provideApplicationScope(
      @MainDispatcher mainDispatcher: CoroutineContext
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)
  }
}
