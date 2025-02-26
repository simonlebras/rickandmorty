package app.rickandmorty.core.coroutines.inject

import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesTo

@ContributesTo(AppScope::class)
public interface CoroutineDispatchersModule {
    @Provides
    @DefaultDispatcher
    public fun provideDefaultDispatcher(): CoroutineContext = Dispatchers.Default

    @Provides
    @IODispatcher
    public fun provideIODispatcher(): CoroutineContext = Dispatchers.IO

    @Provides
    @MainDispatcher
    public fun provideMainDispatcher(): CoroutineContext = Dispatchers.Main

    @Provides
    @MainImmediateDispatcher
    public fun provideMainImmediateDispatcher(): CoroutineContext = Dispatchers.Main.immediate
}
