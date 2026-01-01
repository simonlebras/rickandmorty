package app.rickandmorty.core.datastore

import android.content.Context
import app.rickandmorty.core.metro.AppContext
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import okio.Path
import okio.Path.Companion.toPath

@ContributesBinding(AppScope::class)
public class AndroidFilePathProducer(@AppContext private val context: Context) : FilePathProducer {
  override fun invoke(fileName: String): Path {
    return context.filesDir.resolve(fileName).absolutePath.toPath()
  }
}
