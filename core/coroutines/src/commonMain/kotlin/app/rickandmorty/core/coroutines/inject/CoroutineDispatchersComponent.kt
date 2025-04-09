package app.rickandmorty.core.coroutines.inject

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface CoroutineDispatchersComponent {
  @Provides
  public fun provideDefaultDispatcher(): @DefaultDispatcher CoroutineContext = Dispatchers.Default

  @Provides public fun provideIODispatcher(): @IODispatcher CoroutineContext = Dispatchers.IO

  @Provides public fun provideMainDispatcher(): @MainDispatcher CoroutineContext = Dispatchers.Main

  @Provides
  public fun provideMainImmediateDispatcher(): @MainImmediateDispatcher CoroutineContext =
    Dispatchers.Main.immediate
}
