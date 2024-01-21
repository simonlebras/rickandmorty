package app.rickandmorty.feature.episodes.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.data.model.Episode
import app.rickandmorty.feature.episodes.R

@Composable
internal fun EpisodeListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: EpisodeListViewModel = hiltViewModel(),
) {
    val episodes = viewModel.episodes.collectAsLazyPagingItems()

    ReportDrawnWhen {
        episodes.itemCount > 0 ||
            episodes.loadState.refresh.endOfPaginationReached
    }

    EpisodeListScreen(
        episodes = episodes,
        onNavigateToSettings = onNavigateToSettings,
    )
}

// TODO implement loading/error states
@Composable
private fun EpisodeListScreen(
    episodes: LazyPagingItems<Episode>,
    onNavigateToSettings: () -> Unit,
) {
    Scaffold(
        topBar = {
            EpisodeListScreenAppBar(
                onNavigateToSettings = onNavigateToSettings,
            )
        },
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding),
            contentPadding = padding,
        ) {
            items(
                count = episodes.itemCount,
                key = episodes.itemKey { it.id },
            ) { index ->
                val item = episodes[index]!!

                ListItem(
                    headlineContent = {
                        Text(text = item.name)
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EpisodeListScreenAppBar(
    onNavigateToSettings: () -> Unit,
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
    )
}
