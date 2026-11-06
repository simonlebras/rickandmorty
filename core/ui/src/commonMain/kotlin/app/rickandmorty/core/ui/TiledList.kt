package app.rickandmorty.core.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.PullToRefreshBox
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.list_load_error
import app.rickandmorty.core.l10n.resources.list_refresh_error
import app.rickandmorty.core.l10n.resources.list_revalidation_error
import app.rickandmorty.core.l10n.resources.retry
import app.rickandmorty.core.tiling.TilingLoadError
import app.rickandmorty.core.tiling.TilingState
import org.jetbrains.compose.resources.stringResource

@Composable
public fun <T> TiledList(
  state: TilingState<T>,
  itemKey: (T) -> String,
  onAction: (TilingState.Action) -> Unit,
  modifier: Modifier = Modifier,
  listState: LazyListState = rememberTiledLazyListState(state, itemKey),
  contentPadding: PaddingValues = PaddingValues(),
  empty: @Composable () -> Unit = {},
  item: @Composable LazyItemScope.(T) -> Unit,
) {
  val tilingData = state.tilingData
  val items = tilingData.items
  val loadStates = tilingData.loadStates
  val placeholderModifier = modifier.fillMaxSize().padding(contentPadding)
  when {
    loadStates.refresh.isLoading && items.isEmpty() ->
      Box(placeholderModifier, contentAlignment = Alignment.Center) { Loader() }

    loadStates.refresh.isError && items.isEmpty() ->
      Box(placeholderModifier, contentAlignment = Alignment.Center) {
        Error(
          text = stringResource(L10nRes.string.list_load_error),
          onRetry = { onAction(TilingState.Action.Retry) },
        )
      }

    loadStates.isIdle && items.isEmpty() ->
      PullToRefreshBox(
        isRefreshing = false,
        onRefresh = { onAction(TilingState.Action.Refresh) },
        modifier = modifier,
        indicatorPadding = contentPadding,
      ) {
        LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = contentPadding) {
          item(key = "empty") {
            Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) { empty() }
          }
        }
      }

    else -> {
      TilingEffect(
        listState = listState,
        items = items,
        leadingItemCount = loadStates.leadingItemCount,
        onPageVisible = { page -> onAction(TilingState.Action.LoadAround(page)) },
      )

      PullToRefreshBox(
        isRefreshing = loadStates.refresh.isLoading,
        onRefresh = { onAction(TilingState.Action.Refresh) },
        modifier = modifier,
        indicatorPadding = contentPadding,
      ) {
        LazyColumn(
          state = listState,
          modifier = Modifier.fillMaxSize().consumeWindowInsets(contentPadding),
          contentPadding = contentPadding,
        ) {
          edgeLoadState(
            key = "prepend",
            loadState = loadStates.prepend,
            onRetry = { onAction(TilingState.Action.Retry) },
          )

          items(items = items, key = itemKey, itemContent = item)

          edgeLoadState(
            key = "append",
            loadState = loadStates.append,
            onRetry = { onAction(TilingState.Action.Retry) },
          )
        }
      }
    }
  }
}

public fun <A> TilingLoadError.toUiMessage(action: (TilingState.Action) -> A): UiMessage<A> =
  when (this) {
    is TilingLoadError.Refresh ->
      UiMessage(
        text = L10nRes.string.list_refresh_error,
        actionLabel = L10nRes.string.retry,
        action = action(TilingState.Action.Refresh),
      )
    is TilingLoadError.Revalidation ->
      UiMessage(
        text = L10nRes.string.list_revalidation_error,
        actionLabel = L10nRes.string.retry,
        action = action(TilingState.Action.Retry),
      )
  }

@Composable
public fun <T> rememberTiledLazyListState(
  state: TilingState<T>,
  itemKey: (T) -> String,
): LazyListState =
  rememberTiledLazyListState(
    items = { state.tilingData.items },
    key = itemKey,
    leadingItemCount = { state.tilingData.loadStates.leadingItemCount },
  )

public suspend fun <T> LazyListState.scrollToFirstPage(
  state: TilingState<T>,
  itemKey: (T) -> String,
  onAction: (TilingState.Action) -> Unit,
): Unit =
  scrollToFirstPage(
    items = { state.tilingData.items },
    key = itemKey,
    onPivot = { page -> onAction(TilingState.Action.LoadAround(page)) },
  )
