package app.rickandmorty.coil

import coil.util.DebugLogger
import coil.util.Logger
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
