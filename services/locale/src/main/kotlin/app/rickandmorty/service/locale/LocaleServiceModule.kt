package app.rickandmorty.service.locale

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface LocaleServiceModule {
    @Binds
    fun bindLocalService(impl: LocaleServiceImpl): LocaleService
}
