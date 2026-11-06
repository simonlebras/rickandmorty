package app.rickandmorty.core.tiling

import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import app.rickandmorty.core.paging.Page
import app.rickandmorty.core.paging.PageStatus
import com.tunjid.tiler.PivotRequest
import com.tunjid.tiler.Tile
import com.tunjid.tiler.TiledList
import com.tunjid.tiler.distinctBy
import com.tunjid.tiler.listTiler
import com.tunjid.tiler.queries
import com.tunjid.tiler.toPivotedTileInputs
import com.tunjid.tiler.toTiledList
import kotlin.coroutines.cancellation.CancellationException
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.withIndex
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

private const val ON_COUNT = 3
private const val OFF_COUNT = 2

private val PIVOT_DEBOUNCE = 100.milliseconds
private val REFRESH_RELOAD_TIMEOUT = 10.seconds

internal fun <T> Flow<TilingState.Action>.toTiledItems(
  itemKey: (T) -> String,
  pageSource: (page: Int) -> Flow<Page<T>>,
  refresh: suspend () -> Unit = {},
  initialPage: Int = FIRST_PAGE_KEY,
  seed: TiledItems<T> = TiledItems(),
): Flow<TiledItems<T>> = channelFlow {
  val pivotPage = MutableStateFlow(initialPage.coerceAtLeast(FIRST_PAGE_KEY))
  val retries = MutableStateFlow(0)
  val refreshes = Channel<Unit>(Channel.CONFLATED)
  val refreshing = MutableStateFlow<LoadState?>(null)

  launch {
    this@toTiledItems.collect { action ->
      when (action) {
        is TilingState.Action.LoadAround -> pivotPage.value = action.page
        TilingState.Action.Retry -> {
          refreshing.update { it.takeUnless { it is LoadState.Error } }
          retries.update { it + 1 }
        }
        TilingState.Action.Refresh -> refreshes.send(Unit)
      }
    }
  }

  tiling(itemKey, pageSource, refresh, pivotPage, retries, refreshes, refreshing, seed).collect {
    send(it)
  }
}

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
private fun <T> tiling(
  itemKey: (T) -> String,
  pageSource: (page: Int) -> Flow<Page<T>>,
  refresh: suspend () -> Unit,
  pivotPage: StateFlow<Int>,
  retries: StateFlow<Int>,
  refreshes: ReceiveChannel<Unit>,
  refreshing: MutableStateFlow<LoadState?>,
  seed: TiledItems<T>,
): Flow<TiledItems<T>> = channelFlow {
  val startPage = pivotPage.value
  val maxKnownPage = MutableStateFlow(startPage)
  val reads = MutableStateFlow(emptyMap<Int, Page<T>>())
  val activePage = MutableStateFlow(startPage)
  val settledReads =
    MutableSharedFlow<Int>(extraBufferCapacity = 64, onBufferOverflow = BufferOverflow.DROP_OLDEST)

  fun readPage(page: Int): Flow<List<T>> =
    retries
      .drop(1)
      .filter { reads.value[page]?.status is PageStatus.Failed }
      .onStart { emit(0) }
      .flatMapLatest {
        reads.update { map ->
          val current = map[page]
          if (current != null && current.status is PageStatus.Failed) {
            map + (page to current.copy(status = PageStatus.Loading))
          } else {
            map
          }
        }
        pageSource(page).catch { error ->
          emit(Page(items = emptyList(), nextPage = null, status = PageStatus.Failed(error)))
        }
      }
      .onEach { read ->
        if (read.status == PageStatus.Loaded || read.status is PageStatus.Failed) {
          settledReads.tryEmit(page)
        }
        reads.update { it + (page to read) }
        maxKnownPage.update { it.recording(page, read) }
      }
      .onCompletion { reads.update { it - page } }
      .map { it.items }

  val items: Flow<TiledList<Int, T>> =
    merge(
        flowOf(Tile.Limiter(maxQueries = ON_COUNT, itemSizeHint = null)),
        combine(pivotPage, maxKnownPage) { page, max -> page.coerceIn(FIRST_PAGE_KEY, max) }
          .distinctUntilChanged()
          .withIndex()
          .debounce { [index, _] -> if (index == 0) 0L else PIVOT_DEBOUNCE.inWholeMilliseconds }
          .map { it.value }
          .onEach { page -> activePage.value = page }
          .toPivotedTileInputs(maxKnownPage.map { pivotRequest<T>(maxKnownPage = it) }),
      )
      .toTiledList(
        listTiler(
          order = Tile.Order.PivotSorted(query = startPage, comparator = naturalOrder()),
          fetcher = ::readPage,
        ),
      )
      .onStart { emit(seed.items) }

  launch {
    for (pull in refreshes) {
      refreshing.value = LoadState.Loading
      refreshing.value =
        try {
          coroutineScope {
            val reloaded =
              async(start = CoroutineStart.UNDISPATCHED) {
                settledReads.first { page -> page == activePage.value }
              }
            refresh()
            withTimeoutOrNull(REFRESH_RELOAD_TIMEOUT) { reloaded.await() }
            reloaded.cancel()
          }
          null
        } catch (e: CancellationException) {
          throw e
        } catch (e: Exception) {
          LoadState.Error(e)
        }
    }
  }

  val refreshState: Flow<LoadState> =
    combine(activePage, reads) { page, reads -> reads[page]?.let(::refreshState) }
      .scan(seed.loadStates.refresh) { previous, refresh -> refresh ?: previous }
      .combine(refreshing) { fromReads, refreshing -> refreshing ?: fromReads }

  val tiledItems: Flow<TiledItems<T>> =
    combine(items, refreshState, reads) { items, refresh, reads ->
      val deduped = items.distinctBy(itemKey)
      val pages = deduped.queries()
      TiledItems(
        items = deduped,
        loadStates =
          LoadStates(
            refresh = refresh,
            prepend = edgeState(beyond = pages.firstOrNull()?.let { reads[it - 1] }),
            append = edgeState(beyond = pages.lastOrNull()?.let { reads[it + 1] }),
          ),
        revalidationError =
          reads.values.firstNotNullOfOrNull { read ->
            (read.status as? PageStatus.Failed)?.error?.takeIf { read.items.isNotEmpty() }
          },
      )
    }

  tiledItems.collect { send(it) }
}

private fun Int.recording(page: Int, read: Page<*>): Int {
  val nextPage = read.nextPage
  return when {
    nextPage != null -> maxOf(this, nextPage)
    read.status == PageStatus.Loaded -> minOf(this, page)
    else -> this
  }
}

private fun refreshState(active: Page<*>): LoadState =
  when (val status = active.status) {
    PageStatus.Loading -> LoadState.Loading
    is PageStatus.Failed -> LoadState.Error(status.error)
    else -> LoadState.NotLoading
  }

private fun edgeState(beyond: Page<*>?): LoadState =
  when (val status = beyond?.status) {
    PageStatus.Loading -> LoadState.Loading
    is PageStatus.Failed if beyond.items.isEmpty() -> LoadState.Error(status.error)
    else -> LoadState.NotLoading
  }

private fun <T> pivotRequest(maxKnownPage: Int): PivotRequest<Int, T> =
  PivotRequest(
    onCount = ON_COUNT,
    offCount = OFF_COUNT,
    comparator = naturalOrder(),
    nextQuery = { if (this < maxKnownPage) this + 1 else null },
    previousQuery = { if (this == FIRST_PAGE_KEY) null else this - 1 },
  )
