package app.rickandmorty.data.theme

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okio.FileSystem
import okio.Path

@ContributesBinding(AppScope::class)
public class JvmFilePathProducer : FilePathProducer {
  override fun produceFilePath(fileName: String): Path =
    FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve(fileName)
}
