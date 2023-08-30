package app.rickandmorty.theme.data

import app.rickandmorty.startup.Initializer
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
internal interface NightModeInitializerModule {
    @Binds
    @IntoSet
    fun bindInitializer(nightModeInitializer: NightModeInitializer): Initializer
}
