package app.rickandmorty.feature.episodes.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.PullToRefresh
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.episode_list_empty
import app.rickandmorty.core.l10n.resources.episode_list_title
import app.rickandmorty.core.l10n.resources.navigate_up
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.Error
import app.rickandmorty.core.ui.appendLoadState
import app.rickandmorty.core.ui.errorOrNull
import app.rickandmorty.core.ui.isEmpty
import app.rickandmorty.core.ui.isError
import app.rickandmorty.core.ui.isLoading
import app.rickandmorty.data.model.Episode
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EpisodeListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: EpisodeListViewModel = hiltViewModel(),
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    ReportDrawnWhen { episodes.loadState.isIdle }

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

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            EpisodeListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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

                loadState.isIdle && episodes.isEmpty -> {
                    Empty(
                        graphic = {
                            Icon(
                                imageVector = RamIcons.Outlined.Tv,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                            )
                        },
                        title = {
                            Text(text = stringResource(L10nRes.string.episode_list_empty))
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .consumeWindowInsets(contentPadding),
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
            Text(text = stringResource(L10nRes.string.episode_list_title))
        },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = RamIcons.Filled.Settings,
                    contentDescription = stringResource(L10nRes.string.navigate_up),
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}
