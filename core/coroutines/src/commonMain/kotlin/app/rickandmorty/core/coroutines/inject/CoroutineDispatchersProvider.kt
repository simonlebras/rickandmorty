package app.rickandmorty.core.coroutines.inject

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@ContributesTo(AppScope::class)
public interface CoroutineDispatchersProvider {
  @Provides
  @DefaultDispatcher
  public fun provideDefaultDispatcher(): CoroutineContext = Dispatchers.Default

  @Provides @IODispatcher public fun provideIODispatcher(): CoroutineContext = Dispatchers.IO

  @Provides @MainDispatcher public fun provideMainDispatcher(): CoroutineContext = Dispatchers.Main

  @Provides
  @MainImmediateDispatcher
  public fun provideMainImmediateDispatcher(): CoroutineContext = Dispatchers.Main.immediate
}
