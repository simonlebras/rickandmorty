package app.rickandmorty.core.logging

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import timber.log.Timber
import timber.log.Timber.Tree

@Module
@InstallIn(SingletonComponent::class)
internal object DebugTreeModule {
    @Provides
    @IntoSet
    fun provideTree(): Tree = Timber.DebugTree()
}
