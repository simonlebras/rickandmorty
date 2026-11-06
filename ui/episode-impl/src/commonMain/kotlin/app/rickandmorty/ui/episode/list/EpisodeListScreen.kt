package app.rickandmorty.ui.episode.list

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import app.rickandmorty.core.designsystem.component.SettingsNavButton
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.episode_list_empty
import app.rickandmorty.core.l10n.resources.episode_list_title
import app.rickandmorty.core.tiling.isSettled
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.core.ui.TiledList
import app.rickandmorty.core.ui.UiMessages
import app.rickandmorty.core.ui.rememberTiledLazyListState
import app.rickandmorty.core.ui.scrollToFirstPage
import app.rickandmorty.data.episode.Episode
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun EpisodeListScreen(
  onNavigateToSettings: () -> Unit,
  reselectEvents: Flow<Unit> = emptyFlow(),
  stateHolder: EpisodeListStateHolder = metroViewModel<EpisodeListViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  ReportDrawnWhen { uiState.isSettled }

  EpisodeListContent(
    uiState = uiState,
    onAction = stateHolder.accept,
    onNavigateToSettings = onNavigateToSettings,
    reselectEvents = reselectEvents,
  )
}

@Composable
internal fun EpisodeListContent(
  uiState: EpisodeListUiState,
  onAction: (EpisodeListAction) -> Unit,
  onNavigateToSettings: () -> Unit,
  modifier: Modifier = Modifier,
  reselectEvents: Flow<Unit> = emptyFlow(),
) {
  val listState = rememberTiledLazyListState(uiState, Episode::id)
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  val currentUiState by rememberUpdatedState(uiState)
  val currentOnAction by rememberUpdatedState(onAction)
  LaunchedEffect(reselectEvents) {
    reselectEvents.collect {
      listState.scrollToFirstPage(currentUiState, Episode::id) {
        currentOnAction(EpisodeListAction.Tile(it))
      }
    }
  }

  UiMessages(
    messages = uiState.messages,
    snackbarHostState = snackbarHostState,
    onAction = onAction,
    onMessageShown = { onAction(EpisodeListAction.MessageShown(it)) },
  )

  HazeScaffold(
    modifier = modifier,
    topBar = {
      EpisodeListScreenAppBar(
        onNavigateToSettings = onNavigateToSettings,
        scrollBehavior = scrollBehavior,
      )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { contentPadding ->
    TiledList(
      state = uiState,
      itemKey = Episode::id,
      onAction = { onAction(EpisodeListAction.Tile(it)) },
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      listState = listState,
      contentPadding = contentPadding,
      empty = {
        Empty(
          graphic = {
            Icon(
              painter = painterResource(RamIcons.Outlined.Tv),
              contentDescription = null,
              modifier = Modifier.size(64.dp),
            )
          },
          title = { Text(text = stringResource(L10nRes.string.episode_list_empty)) },
        )
      },
    ) { episode ->
      EpisodeItem(episode)
    }
  }
}

@Composable
private fun EpisodeItem(episode: Episode) {
  ListItem(supportingContent = { Text(text = episode.episode) }) {
    Text(text = episode.name)
  }
}

@Composable
private fun EpisodeListScreenAppBar(
  onNavigateToSettings: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.episode_list_title)) },
    actions = { SettingsNavButton(onClick = onNavigateToSettings) },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
      ),
    scrollBehavior = scrollBehavior,
  )
}
