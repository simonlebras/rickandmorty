package app.rickandmorty.core.ui

import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems

public val <T : Any> LazyPagingItems<T>.isEmpty: Boolean
    get() = itemCount == 0

public val <T : Any> LazyPagingItems<T>.isNotEmpty: Boolean
    get() = itemCount > 0

public val LoadState.isLoading: Boolean
    get() = this is LoadState.Loading

public val LoadState.isNotLoading: Boolean
    get() = this is LoadState.NotLoading

public val LoadState.isError: Boolean
    get() = this is LoadState.Error

public val LoadState.errorOrNull: Throwable?
    get() = (this as? LoadState.Error)?.error
