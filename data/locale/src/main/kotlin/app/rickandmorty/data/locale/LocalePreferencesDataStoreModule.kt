//package app.rickandmorty.data.locale
//
//import android.content.Context
//import androidx.datastore.core.DataStore
//import androidx.datastore.core.DataStoreFactory
//import androidx.datastore.dataStoreFile
//import app.rickandmorty.core.coroutines.inject.ApplicationScope
//import app.rickandmorty.core.coroutines.inject.IODispatcher
//import app.rickandmorty.data.locale.proto.LocalePreferences
//import kotlinx.coroutines.CoroutineDispatcher
//import kotlinx.coroutines.CoroutineScope
//
//private const val PREFERENCES_FILE_NAME = "locale_preferences.pb"
//
//internal object LocalePreferencesDataStoreModule {
//  fun provideLocalePreferencesDataStore(
//    context: Context,
//    @ApplicationScope applicationScope: CoroutineScope,
//    @IODispatcher ioDispatcher: CoroutineDispatcher,
//    localePreferencesSerializer: LocalePreferencesSerializer,
//  ): DataStore<LocalePreferences> =
//    DataStoreFactory.create(
//      serializer = localePreferencesSerializer,
//      scope = CoroutineScope(applicationScope.coroutineContext + ioDispatcher),
//      produceFile = { context.dataStoreFile(PREFERENCES_FILE_NAME) },
//    )
//}
