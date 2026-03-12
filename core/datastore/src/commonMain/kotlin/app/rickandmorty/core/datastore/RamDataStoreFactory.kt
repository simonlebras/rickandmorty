package app.rickandmorty.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.okio.OkioSerializer
import androidx.datastore.core.okio.OkioStorage
import kotlin.coroutines.CoroutineContext
import okio.FileSystem

public object RamDataStoreFactory {
  public fun <T> create(
    fileSystem: FileSystem,
    serializer: OkioSerializer<T>,
    filePathProducer: FilePathProducer,
    fileName: String,
    context: CoroutineContext,
  ): DataStore<T> {
    return DataStore.Builder(
        storage =
          OkioStorage(
            fileSystem = fileSystem,
            serializer = serializer,
            producePath = { filePathProducer(fileName) },
          ),
        context = context,
      )
      .build()
  }
}
