package app.rickandmorty.data.locale.inject

import androidx.datastore.core.DataStore
import app.rickandmorty.core.datastore.FilePathProducer
import app.rickandmorty.core.datastore.RamDataStoreFactory
import app.rickandmorty.data.locale.LocaleProto
import app.rickandmorty.data.locale.LocaleSerializer
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import okio.FileSystem

private const val LOCALE_DATASTORE_FILE_NAME = "locale.pb"

@ContributesTo(AppScope::class)
public interface LocaleDataStoreProvider {
  public companion object {
    @Provides
    @SingleIn(AppScope::class)
    public fun provideLocaleDataStore(
      fileSystem: FileSystem,
      filePathProducer: FilePathProducer,
      serializer: LocaleSerializer,
    ): DataStore<LocaleProto> =
      RamDataStoreFactory.create(
        fileSystem = fileSystem,
        filePathProducer = filePathProducer,
        fileName = LOCALE_DATASTORE_FILE_NAME,
        serializer = serializer,
      )
  }
}
