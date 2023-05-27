package app.rickandmorty.logging

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds
import timber.log.Timber

@Module
@InstallIn(SingletonComponent::class)
internal interface TimberModule {
    @Multibinds
    fun timberTrees(): Set<Timber.Tree>
}
