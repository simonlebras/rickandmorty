package app.rickandmorty.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import okio.FileSystem

public object RamDataStoreFactory {
  public fun <T> create(
    fileSystem: FileSystem,
    filePathProducer: FilePathProducer,
    fileName: String,
    serializer: OkioSerializer<T>,
  ): DataStore<T> =
    DataStoreFactory.create(
      storage =
        OkioStorage(
          fileSystem = fileSystem,
          producePath = { filePathProducer(fileName) },
          serializer = serializer,
        )
    )
}
