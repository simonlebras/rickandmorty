package app.rickandmorty.core.datastore

import okio.Path

public fun interface FilePathProducer {
  public operator fun invoke(fileName: String): Path
}
