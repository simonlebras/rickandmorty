package app.rickandmorty.feature.characters.list

import androidx.activity.compose.ReportDrawnWhen
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.ui.resources.R as UiR
import app.rickandmorty.data.model.Character
import app.rickandmorty.feature.characters.R
import coil3.compose.AsyncImage

@Composable
internal fun CharacterListScreen(
    onNavigateToSettings: () -> Unit,
    viewModel: CharacterListViewModel = hiltViewModel(),
) {
    val characters = viewModel.characters.collectAsLazyPagingItems()

    ReportDrawnWhen {
        characters.itemCount > 0 ||
            characters.loadState.refresh.endOfPaginationReached
    }

    CharacterListScreen(
        characters = characters,
        onNavigateToSettings = onNavigateToSettings,
    )
}

// TODO implement loading/error states
@Composable
private fun CharacterListScreen(
    characters: LazyPagingItems<Character>,
    onNavigateToSettings: () -> Unit,
) {
    Scaffold(
        topBar = {
            CharacterListScreenAppBar(
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
                count = characters.itemCount,
                key = characters.itemKey { it.id },
            ) { index ->
                val item = characters[index]!!

                ListItem(
                    headlineContent = {
                        Text(text = item.name)
                    },
                    leadingContent = {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                        )
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CharacterListScreenAppBar(
    onNavigateToSettings: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.character_list_title))
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
