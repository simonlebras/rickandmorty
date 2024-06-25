package app.rickandmorty.data.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import app.rickandmorty.core.coroutines.ApplicationScope
import app.rickandmorty.core.coroutines.IODispatcher
import app.rickandmorty.data.theme.proto.ThemePreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

private const val PREFERENCES_FILE_NAME = "theme_preferences.pb"

@Module
@InstallIn(SingletonComponent::class)
internal object ThemePreferencesDataStoreModule {
    @Provides
    @Singleton
    fun provideThemePreferencesDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope applicationScope: CoroutineScope,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        themePreferencesSerializer: ThemePreferencesSerializer,
    ): DataStore<ThemePreferences> = DataStoreFactory.create(
        serializer = themePreferencesSerializer,
        scope = CoroutineScope(applicationScope.coroutineContext + ioDispatcher),
        produceFile = { context.dataStoreFile(PREFERENCES_FILE_NAME) },
    )
}
