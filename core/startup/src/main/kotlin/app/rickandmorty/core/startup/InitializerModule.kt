package app.rickandmorty.core.startup

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.Multibinds

@Module
@InstallIn(SingletonComponent::class)
internal interface InitializerModule {
    @Multibinds
    fun initializers(): Set<Initializer>
}
