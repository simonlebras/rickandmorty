package app.rickandmorty.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import kotlin.coroutines.cancellation.CancellationException

public const val FIRST_PAGE_KEY: Int = 1

@OptIn(ExperimentalPagingApi::class)
public class PageKeyedRemoteMediator<T : Any>(
    private val pageResolver: PageResolver<T>,
    private val pageFetcher: PageFetcher,
) : RemoteMediator<Int, T>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, T>,
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> FIRST_PAGE_KEY
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(
                        endOfPaginationReached = true,
                    )
                pageResolver.resolve(lastItem) + 1
            }

            else -> error("Unknown LoadType: $loadType")
        }
        return try {
            val result = pageFetcher.fetch(page)
            MediatorResult.Success(endOfPaginationReached = result.nextPage == null)
        } catch (e: CancellationException) {
            throw e
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }
}
