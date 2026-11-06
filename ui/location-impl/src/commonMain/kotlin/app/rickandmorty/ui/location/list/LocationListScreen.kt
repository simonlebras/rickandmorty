package app.rickandmorty.ui.location.list

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
import app.rickandmorty.core.l10n.resources.location_list_empty
import app.rickandmorty.core.l10n.resources.location_list_title
import app.rickandmorty.core.tiling.isSettled
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.core.ui.TiledList
import app.rickandmorty.core.ui.UiMessages
import app.rickandmorty.core.ui.rememberTiledLazyListState
import app.rickandmorty.core.ui.scrollToFirstPage
import app.rickandmorty.data.location.Location
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun LocationListScreen(
  onNavigateToSettings: () -> Unit,
  reselectEvents: Flow<Unit> = emptyFlow(),
  stateHolder: LocationListStateHolder = metroViewModel<LocationListViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  ReportDrawnWhen { uiState.isSettled }

  LocationListContent(
    uiState = uiState,
    onAction = stateHolder.accept,
    onNavigateToSettings = onNavigateToSettings,
    reselectEvents = reselectEvents,
  )
}

@Composable
internal fun LocationListContent(
  uiState: LocationListUiState,
  onAction: (LocationListAction) -> Unit,
  onNavigateToSettings: () -> Unit,
  modifier: Modifier = Modifier,
  reselectEvents: Flow<Unit> = emptyFlow(),
) {
  val listState = rememberTiledLazyListState(uiState, Location::id)
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  val currentUiState by rememberUpdatedState(uiState)
  val currentOnAction by rememberUpdatedState(onAction)
  LaunchedEffect(reselectEvents) {
    reselectEvents.collect {
      listState.scrollToFirstPage(currentUiState, Location::id) {
        currentOnAction(LocationListAction.Tile(it))
      }
    }
  }

  UiMessages(
    messages = uiState.messages,
    snackbarHostState = snackbarHostState,
    onAction = onAction,
    onMessageShown = { onAction(LocationListAction.MessageShown(it)) },
  )

  HazeScaffold(
    modifier = modifier,
    topBar = {
      LocationListScreenAppBar(
        onNavigateToSettings = onNavigateToSettings,
        scrollBehavior = scrollBehavior,
      )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { contentPadding ->
    TiledList(
      state = uiState,
      itemKey = Location::id,
      onAction = { onAction(LocationListAction.Tile(it)) },
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      listState = listState,
      contentPadding = contentPadding,
      empty = {
        Empty(
          graphic = {
            Icon(
              painter = painterResource(RamIcons.Outlined.Map),
              contentDescription = null,
              modifier = Modifier.size(64.dp),
            )
          },
          title = { Text(text = stringResource(L10nRes.string.location_list_empty)) },
        )
      },
    ) { location ->
      LocationItem(location)
    }
  }
}

@Composable
private fun LocationItem(location: Location) {
  ListItem(supportingContent = { Text(text = location.dimension) }) {
    Text(text = location.name)
  }
}

@Composable
private fun LocationListScreenAppBar(
  onNavigateToSettings: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.location_list_title)) },
    actions = { SettingsNavButton(onClick = onNavigateToSettings) },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
      ),
    scrollBehavior = scrollBehavior,
  )
}
