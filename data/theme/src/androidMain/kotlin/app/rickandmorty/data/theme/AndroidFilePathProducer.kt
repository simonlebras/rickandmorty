package app.rickandmorty.data.theme

import android.content.Context
import app.rickandmorty.core.injectannotations.AppContext
import me.tatarka.inject.annotations.Inject
import okio.Path
import okio.Path.Companion.toPath
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ContributesBinding

@Inject
@ContributesBinding(AppScope::class)
public class AndroidFilePathProducer(@AppContext private val context: Context) : FilePathProducer {
  override fun produceFilePath(fileName: String): Path =
    context.filesDir.resolve(fileName).absolutePath.toPath()
}
