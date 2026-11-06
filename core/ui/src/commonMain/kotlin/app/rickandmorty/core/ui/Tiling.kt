package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.list_load_error
import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import app.rickandmorty.core.tiling.LoadState
import app.rickandmorty.core.tiling.LoadStates
import com.tunjid.tiler.TiledList
import com.tunjid.tiler.queryAtOrNull
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withTimeoutOrNull
import org.jetbrains.compose.resources.stringResource

private val PIVOT_TIMEOUT = 5.seconds
private val RESTORE_TIMEOUT = 10.seconds

internal val LoadState.isLoading: Boolean
  get() = this is LoadState.Loading

internal val LoadState.isError: Boolean
  get() = this is LoadState.Error

internal val LoadStates.leadingItemCount: Int
  get() = if (prepend.isLoading || prepend.isError) 1 else 0

internal fun LazyListScope.edgeLoadState(key: String, loadState: LoadState, onRetry: () -> Unit) {
  when (loadState) {
    LoadState.Loading ->
      item(key = key, contentType = "loader") {
        Loader(
          modifier =
            Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally).padding(16.dp),
        )
      }
    is LoadState.Error ->
      item(key = key, contentType = "error") {
        Error(
          text = stringResource(L10nRes.string.list_load_error),
          onRetry = onRetry,
          modifier =
            Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally).padding(16.dp),
        )
      }
    is LoadState.NotLoading -> Unit
  }
}

@Composable
internal fun <T> TilingEffect(
  listState: LazyListState,
  items: TiledList<Int, T>,
  leadingItemCount: Int,
  onPageVisible: (Int) -> Unit,
) {
  val currentItems by rememberUpdatedState(items)
  val currentLeadingItemCount by rememberUpdatedState(leadingItemCount)
  val currentOnPageVisible by rememberUpdatedState(onPageVisible)
  LaunchedEffect(listState) {
    snapshotFlow {
      val firstVisible =
        (listState.firstVisibleItemIndex - currentLeadingItemCount).coerceAtLeast(0)
      val lastVisible =
        listState.layoutInfo.visibleItemsInfo.lastOrNull()?.let {
          (it.index - currentLeadingItemCount).coerceAtLeast(0)
        }
      currentItems.queryAtOrNull(firstVisible) ?: lastVisible?.let(currentItems::queryAtOrNull)
    }
      .distinctUntilChanged()
      .collect { page -> page?.let(currentOnPageVisible) }
  }
}

internal suspend fun <T> LazyListState.scrollToFirstPage(
  items: () -> TiledList<Int, T>,
  key: (T) -> String,
  onPivot: (page: Int) -> Unit,
) {
  if (items().isEmpty()) return
  withTimeoutOrNull(PIVOT_TIMEOUT) {
    if (items().queryAtOrNull(0) != FIRST_PAGE_KEY) {
      onPivot(FIRST_PAGE_KEY)
      snapshotFlow(items).first { it.isEmpty() || it.queryAt(0) == FIRST_PAGE_KEY }
    }
    snapshotFlow { layoutInfo.visibleItemsInfo.firstOrNull() }
      .first { info -> info == null || items().getOrNull(info.index)?.let(key) == info.key }
  }
  scrollToItem(0)
}

@Composable
internal fun <T> rememberTiledLazyListState(
  items: () -> TiledList<Int, T>,
  key: (T) -> String,
  leadingItemCount: () -> Int,
): LazyListState {
  val listState = rememberLazyListState()
  val currentItems by rememberUpdatedState(items)
  val currentKey by rememberUpdatedState(key)
  val currentLeadingItemCount by rememberUpdatedState(leadingItemCount)
  val restoreTo =
    rememberSaveable(
      saver =
        Saver<MutableState<ScrollAnchor?>, List<Any>>(
          save = { pending ->
            val anchor =
              pending.value
                ?: run {
                  val index = listState.firstVisibleItemIndex - currentLeadingItemCount()
                  val offset = if (index < 0) 0 else listState.firstVisibleItemScrollOffset
                  currentItems().getOrNull(index.coerceAtLeast(0))?.let { item ->
                    ScrollAnchor(currentKey(item), offset)
                  }
                }
            anchor?.let { listOf(it.key, it.offset) } ?: emptyList()
          },
          restore = { saved ->
            mutableStateOf(
              saved.takeIf { it.size == 2 }?.let { ScrollAnchor(it[0] as String, it[1] as Int) },
            )
          },
        ),
    ) {
      mutableStateOf(null)
    }

  restoreTo.value?.let { anchor ->
    LaunchedEffect(anchor) {
      withTimeoutOrNull(RESTORE_TIMEOUT) {
        val index =
          snapshotFlow {
            currentItems().indexOfFirst { currentKey(it) == anchor.key }
          }
            .first { it >= 0 } + currentLeadingItemCount()
        if (index != listState.firstVisibleItemIndex) listState.scrollToItem(index, anchor.offset)
      }
      restoreTo.value = null
    }
  }
  return listState
}

private class ScrollAnchor(val key: String, val offset: Int)
