package app.rickandmorty.logging

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import timber.log.Timber

internal class TimberInitializer : Initializer<Unit> {
    @Inject
    lateinit var timberTrees: Set<@JvmSuppressWildcards Timber.Tree>

    override fun create(context: Context) {
        EntryPointAccessors
            .fromApplication<TimberInitializerEntryPoint>(context)
            .inject(this)

        Timber.plant(*timberTrees.toTypedArray())
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface TimberInitializerEntryPoint {
    fun inject(initializer: TimberInitializer)
}
