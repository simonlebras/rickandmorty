package app.rickandmorty.feature.episodes.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.component.Error
import app.rickandmorty.core.designsystem.component.HazeScaffold
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.PullToRefresh
import app.rickandmorty.core.designsystem.component.appendLoadState
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.metrics.TrackScrollJank
import app.rickandmorty.core.ui.errorOrNull
import app.rickandmorty.core.ui.isEmpty
import app.rickandmorty.core.ui.isError
import app.rickandmorty.core.ui.isLoading
import app.rickandmorty.core.ui.isNotLoading
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.data.model.Episode
import app.rickandmorty.feature.episodes.R

@Composable
internal fun EpisodeListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: EpisodeListViewModel = hiltViewModel(),
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    ReportDrawnWhen { !episodes.loadState.refresh.isLoading }

    EpisodeListScreen(
        episodes = episodes,
        onNavigateToSettings = onNavigateToSettings,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodeListScreen(
    episodes: LazyPagingItems<Episode>,
    onNavigateToSettings: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val loadState = episodes.loadState

    loadState.refresh.errorOrNull?.let { error ->
        LaunchedEffect(snackbarHostState, error) {
            snackbarHostState.showSnackbar("Error")
        }
    }
    loadState.append.errorOrNull?.let { error ->
        LaunchedEffect(snackbarHostState, error) {
            snackbarHostState.showSnackbar("Error")
        }
    }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    HazeScaffold(
        topBar = {
            EpisodeListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        blurTopBar = true,
    ) { contentPadding ->
        PullToRefresh(
            isRefreshing = loadState.refresh.isLoading,
            onRefresh = episodes::refresh,
            indicatorPadding = contentPadding,
        ) {
            when {
                loadState.refresh.isLoading && episodes.isEmpty -> {
                    Loader(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.refresh.isError && episodes.isEmpty -> {
                    Error(
                        text = "Error",
                        onRetry = episodes::retry,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.refresh.isNotLoading && episodes.isEmpty -> {
                    // Todo empty state
                }

                else -> {
                    val listState = rememberLazyListState()

                    TrackScrollJank(
                        scrollableState = listState,
                        stateName = "episodeList:screen",
                    )

                    LazyColumn(
                        modifier = Modifier
                            .nestedScroll(scrollBehavior.nestedScrollConnection)
                            .fillMaxSize()
                            .consumeWindowInsets(contentPadding),
                        state = listState,
                        contentPadding = contentPadding,
                    ) {
                        items(
                            count = episodes.itemCount,
                            key = episodes.itemKey { it.id },
                            contentType = episodes.itemContentType { "episode" },
                        ) { index ->
                            val item = episodes[index]!!
                            EpisodeItem(item)
                        }

                        appendLoadState(items = episodes)
                    }
                }
            }
        }
    }
}

@Composable
private fun EpisodeItem(episode: Episode) {
    ListItem(
        headlineContent = {
            Text(text = episode.name)
        },
        supportingContent = {
            Text(text = episode.episode)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodeListScreenAppBar(
    onNavigateToSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.episode_list_title))
        },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = RamIcons.Filled.Settings,
                    contentDescription = stringResource(UiR.string.navigate_up),
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent,
        ),
        scrollBehavior = scrollBehavior,
    )
}
