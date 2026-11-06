package app.rickandmorty.core.tiling

import app.rickandmorty.core.paging.Page
import com.tunjid.mutator.coroutines.launchedCollect
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

context(productionScope: CoroutineScope)
public fun <T> Flow<TilingState.Action>.launchTilingMutations(
  state: TilingState<T>,
  itemKey: (T) -> String,
  pageSource: (page: Int) -> Flow<Page<T>>,
  refresh: suspend () -> Unit,
  context: CoroutineContext,
  onLoadError: (TilingLoadError) -> Unit,
  onPageChanged: (page: Int) -> Unit = {},
) {
  val data = state.tilingData.requireMutable()

  onEach { action ->
      if (action is TilingState.Action.LoadAround) {
        data.currentPage = action.page
        onPageChanged(action.page)
      }
    }
    .toTiledItems(
      itemKey = itemKey,
      pageSource = pageSource,
      refresh = refresh,
      initialPage = data.currentPage,
      seed = TiledItems(items = data.items, loadStates = data.loadStates),
    )
    .flowOn(context)
    .onDistinctError(TiledItems<T>::shownRefreshError) { error ->
      onLoadError(TilingLoadError.Refresh(error))
    }
    .onDistinctError(TiledItems<T>::revalidationError) { error ->
      onLoadError(TilingLoadError.Revalidation(error))
    }
    .launchedCollect { tiledItems ->
      data.items = tiledItems.items
      data.loadStates = tiledItems.loadStates
    }
}
