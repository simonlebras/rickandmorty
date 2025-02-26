package app.rickandmorty.data.theme

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import app.rickandmorty.core.coroutines.inject.ApplicationScope
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.data.theme.proto.ThemePreferences
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope

private const val PREFERENCES_FILE_NAME = "theme_preferences.pb"

internal object ThemePreferencesDataStoreModule {
    fun provideThemePreferencesDataStore(
        context: Context,
        @ApplicationScope applicationScope: CoroutineScope,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        themePreferencesSerializer: ThemePreferencesSerializer,
    ): DataStore<ThemePreferences> = DataStoreFactory.create(
        serializer = themePreferencesSerializer,
        scope = CoroutineScope(applicationScope.coroutineContext + ioDispatcher),
        produceFile = { context.dataStoreFile(PREFERENCES_FILE_NAME) },
    )
}
