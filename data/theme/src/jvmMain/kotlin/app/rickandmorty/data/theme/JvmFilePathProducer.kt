package app.rickandmorty.data.theme

import me.tatarka.inject.annotations.Inject
import okio.FileSystem
import okio.Path
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
public class JvmFilePathProducer : FilePathProducer {
  override fun produceFilePath(fileName: String): Path =
    FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve(fileName)
}
