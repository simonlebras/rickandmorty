package app.rickandmorty.feature.characters.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.component.AsyncImage
import app.rickandmorty.core.designsystem.component.HazeScaffold
import app.rickandmorty.core.designsystem.component.Loader
import app.rickandmorty.core.designsystem.component.PullToRefresh
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.R as L10nR
import app.rickandmorty.core.metrics.TrackScrollJank
import app.rickandmorty.core.ui.CharacterStatusIndicator
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.Error
import app.rickandmorty.core.ui.appendLoadState
import app.rickandmorty.core.ui.errorOrNull
import app.rickandmorty.core.ui.isEmpty
import app.rickandmorty.core.ui.isError
import app.rickandmorty.core.ui.isLoading
import app.rickandmorty.core.ui.isNotLoading
import app.rickandmorty.core.ui.label
import app.rickandmorty.data.model.Character

@Composable
internal fun CharacterListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: CharacterListViewModel = hiltViewModel(),
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    ReportDrawnWhen { !characters.loadState.refresh.isLoading }

    CharacterListScreen(
        characters = characters,
        onNavigateToSettings = onNavigateToSettings,
    )
}

// Todo localize errors
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreen(
    characters: LazyPagingItems<Character>,
    onNavigateToSettings: () -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val loadState = characters.loadState

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
            CharacterListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
                scrollBehavior = scrollBehavior,
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        blurTopBar = true,
    ) { contentPadding ->
        PullToRefresh(
            isRefreshing = loadState.refresh.isLoading,
            onRefresh = characters::refresh,
            indicatorPadding = contentPadding,
        ) {
            when {
                loadState.refresh.isLoading && characters.isEmpty -> {
                    Loader(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.refresh.isError && characters.isEmpty -> {
                    Error(
                        text = "Error",
                        onRetry = characters::retry,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                            .padding(contentPadding),
                    )
                }

                loadState.refresh.isNotLoading && characters.isEmpty -> {
                    Empty(
                        graphic = {
                            Icon(
                                imageVector = RamIcons.Outlined.Face,
                                contentDescription = null,
                                modifier = Modifier.size(64.dp),
                            )
                        },
                        title = {
                            Text(text = stringResource(L10nR.string.character_list_empty))
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
                        stateName = "characterList:screen",
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
                            count = characters.itemCount,
                            key = characters.itemKey { it.id },
                            contentType = characters.itemContentType { "character" },
                        ) { index ->
                            val item = characters[index]!!
                            CharacterItem(item)
                        }

                        appendLoadState(items = characters)
                    }
                }
            }
        }
    }
}

@Composable
private fun CharacterItem(character: Character) {
    ListItem(
        headlineContent = {
            Text(text = character.name)
        },
        supportingContent = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                CharacterStatusIndicator(status = character.status)

                Text(text = stringResource(character.status.label))
            }
        },
        leadingContent = {
            AsyncImage(
                model = character.image,
                contentDescription = null,
                modifier = Modifier
                    .size(64.dp)
                    .clip(MaterialTheme.shapes.small),
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreenAppBar(
    onNavigateToSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(L10nR.string.character_list_title))
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
