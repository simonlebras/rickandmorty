package app.rickandmorty.data.theme.inject

import androidx.datastore.core.DataStore
import app.rickandmorty.core.coroutines.inject.IODispatcher
import app.rickandmorty.core.datastore.FilePathProducer
import app.rickandmorty.core.datastore.RamDataStoreFactory
import app.rickandmorty.data.theme.ThemeProto
import app.rickandmorty.data.theme.ThemeSerializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.SupervisorJob
import okio.FileSystem

private const val THEME_DATASTORE_FILE_NAME = "theme.pb"

@ContributesTo(AppScope::class)
public interface ThemeDataStoreProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideThemeDataStore(
      fileSystem: FileSystem,
      filePathProducer: FilePathProducer,
      @IODispatcher ioDispatcher: CoroutineContext,
    ): DataStore<ThemeProto> =
      RamDataStoreFactory.create(
        fileSystem = fileSystem,
        serializer = ThemeSerializer(),
        filePathProducer = filePathProducer,
        fileName = THEME_DATASTORE_FILE_NAME,
        context = ioDispatcher + SupervisorJob(),
      )
  }
}
