package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import app.rickandmorty.core.ui.errorOrNull
import app.rickandmorty.core.ui.isLoading

public fun <T : Any> LazyListScope.appendLoadState(
    items: LazyPagingItems<T>,
) {
    val loadState = items.loadState

    if (loadState.append.isLoading) {
        item {
            Loader(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )
        }
    }

    loadState.append.errorOrNull?.let { error ->
        item {
            Error(
                text = "Error",
                onRetry = items::retry,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )
        }
    }
}
