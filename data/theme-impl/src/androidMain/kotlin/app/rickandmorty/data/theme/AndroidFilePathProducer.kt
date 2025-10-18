package app.rickandmorty.data.theme

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okio.Path
import okio.Path.Companion.toPath

@ContributesBinding(AppScope::class)
public class AndroidFilePathProducer(@AppContext private val context: Context) : FilePathProducer {
  override fun produceFilePath(fileName: String): Path =
    context.filesDir.resolve(fileName).absolutePath.toPath()
}
