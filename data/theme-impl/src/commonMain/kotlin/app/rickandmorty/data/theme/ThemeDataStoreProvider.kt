package app.rickandmorty.data.theme

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
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
      serializer: ThemeSerializer,
    ): DataStore<ThemeProto> =
      DataStoreFactory.create(
        storage =
          OkioStorage(
            fileSystem = fileSystem,
            producePath = { filePathProducer.produceFilePath(THEME_DATASTORE_FILE_NAME) },
            serializer = serializer,
          )
      )
  }
}
