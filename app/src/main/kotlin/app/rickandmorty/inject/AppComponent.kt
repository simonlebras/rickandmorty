package app.rickandmorty.inject

import android.content.Context
import app.rickandmorty.core.injectannotations.AppContext
import app.rickandmorty.core.startup.Initializer
import coil3.ImageLoader
import me.tatarka.inject.annotations.Component
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@Component
@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppComponent(
    @get:Provides protected val context: @AppContext Context,
) : AppComponentMerged {
    abstract val imageLoader: ImageLoader

    abstract val initializers: Set<Initializer>

    companion object
}
