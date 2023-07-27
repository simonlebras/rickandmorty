package app.rickandmorty.logging

import app.rickandmorty.startup.Initializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface TimberInitializerModule {
    @get:Binds
    @get:IntoSet
    val TimberInitializer.bindInitializer: Initializer
}
