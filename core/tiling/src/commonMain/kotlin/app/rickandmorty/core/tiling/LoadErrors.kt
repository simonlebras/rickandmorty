package app.rickandmorty.core.tiling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal val TiledItems<*>.shownRefreshError: Throwable?
  get() =
    (loadStates.refresh as? LoadState.Error)?.error?.takeIf {
      !isEmpty && revalidationError == null
    }

internal fun <T> Flow<T>.onDistinctError(
  errorSelector: (T) -> Throwable?,
  action: suspend (error: Throwable) -> Unit,
): Flow<T> = flow {
  var previous: Throwable? = null
  collect { value ->
    val current = errorSelector(value)
    if (current == null) {
      previous = null
    } else if (current !== previous) {
      previous = current
      action(current)
    }
    emit(value)
  }
}
