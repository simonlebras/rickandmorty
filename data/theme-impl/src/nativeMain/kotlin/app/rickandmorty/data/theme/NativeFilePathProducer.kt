package app.rickandmorty.data.theme

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import kotlinx.cinterop.ExperimentalForeignApi
import okio.Path
import okio.Path.Companion.toPath
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@Inject
@ContributesBinding(AppScope::class)
public class NativeFilePathProducer : FilePathProducer {
  @OptIn(ExperimentalForeignApi::class)
  override fun produceFilePath(fileName: String): Path {
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
