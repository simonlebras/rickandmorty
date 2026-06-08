package app.rickandmorty.data.debug.inject

import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.datastore.FilePathProducer
import app.rickandmorty.core.datastore.RamDataStoreFactory
import app.rickandmorty.data.debug.DebugSettingsProto
import app.rickandmorty.data.debug.DebugSettingsSerializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem

private const val DEBUG_SETTINGS_DATASTORE_FILE_NAME = "debug_settings.pb"

@ContributesTo(AppScope::class)
public interface DebugSettingsDataStoreProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideDebugSettingsDataStore(
      fileSystem: FileSystem,
      filePathProducer: FilePathProducer,
      @IODispatcher ioDispatcher: CoroutineContext,
    ): DataStore<DebugSettingsProto> =
      RamDataStoreFactory.create(
        fileSystem = fileSystem,
        serializer = DebugSettingsSerializer(),
        filePathProducer = filePathProducer,
        fileName = DEBUG_SETTINGS_DATASTORE_FILE_NAME,
        context = ioDispatcher + SupervisorJob(),
      )
  }
}
