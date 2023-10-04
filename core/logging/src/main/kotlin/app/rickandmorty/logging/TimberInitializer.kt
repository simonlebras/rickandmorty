package app.rickandmorty.logging

import app.rickandmorty.startup.Initializer
import javax.inject.Inject
import timber.log.Timber

internal class TimberInitializer @Inject constructor(
    private val timberTrees: Set<@JvmSuppressWildcards Timber.Tree>,
) : Initializer {
    override fun initialize() {
        Timber.plant(*timberTrees.toTypedArray())
    }
}
