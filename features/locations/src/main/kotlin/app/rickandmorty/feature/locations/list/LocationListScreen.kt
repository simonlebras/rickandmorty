package app.rickandmorty.feature.locations.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.component.HazeScaffold
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.PullToRefresh
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.core.metrics.TrackScrollJank
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.Error
import app.rickandmorty.core.ui.appendLoadState
import app.rickandmorty.core.ui.errorOrNull
import app.rickandmorty.core.ui.isEmpty
import app.rickandmorty.core.ui.isError
import app.rickandmorty.core.ui.isLoading
import app.rickandmorty.data.model.Location

@Composable
internal fun LocationListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: LocationListViewModel = hiltViewModel(),
) {
    val locations = viewModel.locations.collectAsLazyPagingItems()

    ReportDrawnWhen { locations.loadState.isIdle }

    LocationListScreen(
        locations = locations,
        onNavigateToSettings = onNavigateToSettings,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationListScreen(
    locations: LazyPagingItems<Location>,
    onNavigateToSettings: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val loadState = locations.loadState

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
            LocationListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        blurTopBar = true,
    ) { contentPadding ->
        PullToRefresh(
            isRefreshing = loadState.refresh.isLoading,
            onRefresh = locations::refresh,
            indicatorPadding = contentPadding,
        ) {
            when {
                loadState.refresh.isLoading && locations.isEmpty -> {
                    Loader(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.refresh.isError && locations.isEmpty -> {
                    Error(
                        text = "Error",
                        onRetry = locations::retry,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.isIdle && locations.isEmpty -> {
                    Empty(
                        graphic = {
                            Icon(
                                imageVector = RamIcons.Outlined.Map,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                            )
                        },
                        title = {
                            Text(text = stringResource(L10nR.string.location_list_empty))
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                else -> {
                    val listState = rememberLazyListState()

                    TrackScrollJank(
                        scrollableState = listState,
                        stateName = "locationList:screen",
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
                            count = locations.itemCount,
                            key = locations.itemKey { it.id },
                            contentType = locations.itemContentType { "location" },
                        ) { index ->
                            val item = locations[index]!!
                            LocationItem(item)
                        }

                        appendLoadState(items = locations)
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationItem(location: Location) {
    ListItem(
        headlineContent = {
            Text(text = location.name)
        },
        supportingContent = {
            Text(text = location.dimension)
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationListScreenAppBar(
    onNavigateToSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(L10nR.string.location_list_title))
        },
        actions = {
            IconButton(onClick = onNavigateToSettings) {
                Icon(
                    imageVector = RamIcons.Filled.Settings,
                    contentDescription = stringResource(L10nR.string.navigate_up),
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
