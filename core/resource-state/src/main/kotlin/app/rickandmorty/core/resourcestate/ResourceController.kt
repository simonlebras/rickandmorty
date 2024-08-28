package app.rickandmorty.core.resourcestate

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

public class ResourceController<T>(
    private val resource: Flow<T>,
) {
    public constructor(resource: suspend () -> T) : this(resource.asFlow())

    private val refreshTrigger = MutableSharedFlow<Unit>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    ).apply {
        tryEmit(Unit)
    }

    public fun refresh() {
        refreshTrigger.tryEmit(Unit)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    public val state: Flow<ResourceState<T>> = flow {
        var prevState: ResourceState<T> = Uninitialized
        emitAll(
            refreshTrigger
                .flatMapLatest {
                    resource
                        .map<T, ResourceState<T>>(::Success)
                        .onStart { emit(Loading()) }
                        .catch { error ->
                            emit(Fail(error = error))
                        }
                }.map { state ->
                    state.combine(prevState).also {
                        prevState = it
                    }
                },
        )
    }
}

private fun <T> ResourceState<T>.combine(state: ResourceState<T>) = when (this) {
    is Loading -> copy(value = state.invoke())
    is Fail -> copy(value = state.invoke())
    else -> this
}
