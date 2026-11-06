package app.rickandmorty.data.store

import app.rickandmorty.core.paging.Page
import app.rickandmorty.core.paging.PageStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.scan
import org.mobilenativefoundation.store6.core.StoreError
import org.mobilenativefoundation.store6.core.StoreResult

public fun <V : Any, T> Flow<StoreResult<V>>.toPages(
  items: (V) -> List<T>,
  nextPage: (V) -> Int?,
): Flow<Page<T>> =
  scan<StoreResult<V>, Page<T>?>(null) { last, result ->
      when (result) {
        is StoreResult.Loading -> last.withStatus(PageStatus.Loading)
        is StoreResult.Data -> {
          val status =
            when {
              result.refreshing -> PageStatus.Loading
              result.isStale -> PageStatus.Idle
              else -> PageStatus.Loaded
            }
          Page(items = items(result.value), nextPage = nextPage(result.value), status = status)
        }
        is StoreResult.Revalidated -> last?.copy(status = PageStatus.Loaded)
        is StoreResult.Error ->
          when (val error = result.error) {
            is StoreError.Missing -> Page(items = emptyList(), nextPage = null)
            else -> last.withStatus(PageStatus.Failed(error.toThrowable()))
          }
      }
    }
    .filterNotNull()

public class StoreLoadException(message: String, cause: Throwable? = null) :
  RuntimeException(message, cause)

private fun <T> Page<T>?.withStatus(status: PageStatus): Page<T> =
  this?.copy(status = status) ?: Page(items = emptyList(), nextPage = null, status = status)

private fun StoreError.toThrowable(): Throwable =
  when (this) {
    is StoreError.Fetch -> cause ?: StoreLoadException(message)
    is StoreError.Persistence -> StoreLoadException(message, cause)
    is StoreError.Conversion -> StoreLoadException(message, cause)
    is StoreError.FreshnessUnsatisfiable -> StoreLoadException(message)
    is StoreError.Conflict -> StoreLoadException(message)
    is StoreError.Missing -> StoreLoadException(message)
  }
