package app.rickandmorty.strictmode

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
internal object StrictModeExecutorModule {
    @Provides
    @StrictModeExecutor
    fun provideStrictModeExecutor(): ExecutorService = Executors.newSingleThreadExecutor()
}
