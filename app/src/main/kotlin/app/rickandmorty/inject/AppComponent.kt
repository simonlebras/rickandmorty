package app.rickandmorty.inject

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import app.rickandmorty.core.injectannotations.AppContext
import app.rickandmorty.core.startup.Initializer
import coil3.ImageLoader
import me.tatarka.inject.annotations.Provides
import software.amazon.lastmile.kotlin.inject.anvil.AppScope
import software.amazon.lastmile.kotlin.inject.anvil.ForScope
import software.amazon.lastmile.kotlin.inject.anvil.MergeComponent
import software.amazon.lastmile.kotlin.inject.anvil.SingleIn

@MergeComponent(AppScope::class)
@SingleIn(AppScope::class)
abstract class AppComponent(
    @get:Provides val application: Application,
) {
    abstract val imageLoader: ImageLoader

    abstract val initializers: Set<Initializer>

    @ForScope(AppScope::class)
    abstract val viewModelFactory: ViewModelProvider.Factory

    abstract val activityComponentFactory: ActivityComponent.Factory

    val Application.bind: @AppContext Context
        @Provides get() = this
}
