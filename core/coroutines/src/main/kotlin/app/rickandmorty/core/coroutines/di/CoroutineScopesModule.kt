package app.rickandmorty.core.coroutines.di

import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.coroutines.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

internal object CoroutineScopesModule {
    @ApplicationScope
    fun provideApplicationScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher,
    ): CoroutineScope = CoroutineScope(SupervisorJob() + mainDispatcher)
}
