package app.rickandmorty.core.datastore

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@ContributesBinding(AppScope::class)
public class NativeFilePathProducer : FilePathProducer {
  @OptIn(ExperimentalForeignApi::class)
  override fun invoke(fileName: String): Path {
    val documentDirectory =
      NSFileManager.defaultManager.URLForDirectory(
        directory = NSDocumentDirectory,
        inDomain = NSUserDomainMask,
        appropriateForURL = null,
        create = false,
        error = null,
      )
    return "${documentDirectory!!.path}/$fileName".toPath()
  }
}
