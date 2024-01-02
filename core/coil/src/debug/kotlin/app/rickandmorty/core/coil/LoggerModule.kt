package app.rickandmorty.core.coil

import coil3.util.DebugLogger
import coil3.util.Logger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object LoggerModule {
    @Provides
    fun provideLogger(): Logger = DebugLogger()
}
