package app.rickandmorty.core.datastore

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okio.FileSystem
import okio.Path

@ContributesBinding(AppScope::class)
public class JvmFilePathProducer : FilePathProducer {
  override fun invoke(fileName: String): Path {
    return FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve(fileName)
  }
}
