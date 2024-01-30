package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun PullToRefresh(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    state: PullToRefreshState = rememberPullToRefreshState(),
    indicatorPadding: PaddingValues = PaddingValues(0.dp),
    content: @Composable BoxScope.() -> Unit,
) {
    if (state.isRefreshing) {
        LaunchedEffect(onRefresh) {
            onRefresh()
        }
    }

    if (!isRefreshing && state.isRefreshing) {
        LaunchedEffect(state) {
            state.endRefresh()
        }
    }

    Box(modifier = modifier.nestedScroll(connection = state.nestedScrollConnection)) {
        content()

        PullToRefreshContainer(
            state = state,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(indicatorPadding),
        )
    }
}
