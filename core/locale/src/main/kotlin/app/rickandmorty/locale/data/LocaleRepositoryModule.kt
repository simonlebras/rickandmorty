package app.rickandmorty.locale.data

import app.rickandmorty.locale.domain.LocaleRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LocaleRepositoryModule {
    @Binds
    fun bindLocaleRepository(localeRepository: LocaleRepositoryImpl): LocaleRepository
}
