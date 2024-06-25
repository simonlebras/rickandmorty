package app.rickandmorty.data.locale

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.coroutines.IODispatcher
import app.rickandmorty.data.locale.proto.LocalePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

private const val PREFERENCES_FILE_NAME = "locale_preferences.pb"

@Module
@InstallIn(SingletonComponent::class)
internal object LocalePreferencesDataStoreModule {
    @Provides
    @Singleton
    fun provideLocalePreferencesDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope applicationScope: CoroutineScope,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        localePreferencesSerializer: LocalePreferencesSerializer,
    ): DataStore<LocalePreferences> = DataStoreFactory.create(
        serializer = localePreferencesSerializer,
        scope = CoroutineScope(applicationScope.coroutineContext + ioDispatcher),
        produceFile = { context.dataStoreFile(PREFERENCES_FILE_NAME) },
    )
}
