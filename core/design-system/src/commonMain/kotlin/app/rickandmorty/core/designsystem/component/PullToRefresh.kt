package app.rickandmorty.core.designsystem.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults.Indicator
import androidx.compose.material3.pulltorefresh.PullToRefreshState
import androidx.compose.material3.pulltorefresh.pullToRefresh
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
  Box(
    modifier =
      modifier.pullToRefresh(state = state, isRefreshing = isRefreshing, onRefresh = onRefresh)
  ) {
    content()

    Indicator(
      state = state,
      isRefreshing = isRefreshing,
      modifier = Modifier.align(Alignment.TopCenter).padding(indicatorPadding),
    )
  }
}
