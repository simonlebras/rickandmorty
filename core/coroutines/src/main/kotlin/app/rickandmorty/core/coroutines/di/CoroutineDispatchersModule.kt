package app.rickandmorty.core.coroutines.di

import app.rickandmorty.core.coroutines.DefaultDispatcher
import app.rickandmorty.core.coroutines.IODispatcher
import app.rickandmorty.core.coroutines.MainDispatcher
import app.rickandmorty.core.coroutines.MainImmediateDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

internal object CoroutineDispatchersModule {
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IODispatcher
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}
