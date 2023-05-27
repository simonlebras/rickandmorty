package app.rickandmorty.logging

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LoggerModule {
    @get:Binds
    val TimberLogger.bindLogger: Logger
}
