package app.rickandmorty.strictmode

import app.rickandmorty.startup.Initializer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Module
@InstallIn(SingletonComponent::class)
internal interface StrictModeInitializerModule {
    @Binds
    @IntoSet
    fun bindInitializer(strictModeInitializer: StrictModeInitializer): Initializer

    companion object {
        @Provides
        @StrictModeExecutor
        fun provideStrictModeExecutor(): ExecutorService = Executors.newSingleThreadExecutor()
    }
}
