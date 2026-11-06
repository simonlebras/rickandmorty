package app.rickandmorty.ui.character.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import app.rickandmorty.core.designsystem.component.AsyncImage
import app.rickandmorty.core.designsystem.component.SettingsNavButton
import app.rickandmorty.core.designsystem.icon.RamIcons
import app.rickandmorty.core.designsystem.theme.RamTheme
import app.rickandmorty.core.l10n.resources.Res as L10nRes
import app.rickandmorty.core.l10n.resources.character_list_empty
import app.rickandmorty.core.l10n.resources.character_list_title
import app.rickandmorty.core.tiling.isSettled
import app.rickandmorty.core.ui.CharacterStatusIndicator
import app.rickandmorty.core.ui.Empty
import app.rickandmorty.core.ui.HazeScaffold
import app.rickandmorty.core.ui.ReportDrawnWhen
import app.rickandmorty.core.ui.TiledList
import app.rickandmorty.core.ui.UiMessages
import app.rickandmorty.core.ui.label
import app.rickandmorty.core.ui.rememberTiledLazyListState
import app.rickandmorty.core.ui.scrollToFirstPage
import app.rickandmorty.core.ui.tooling.preview.ProvideColorImagePreviewHandler
import app.rickandmorty.data.character.Character
import com.tunjid.mutator.compose.produceStateWithLifecycle
import dev.zacsweers.metrox.viewmodel.metroViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CharacterListScreen(
  onNavigateToSettings: () -> Unit,
  reselectEvents: Flow<Unit> = emptyFlow(),
  stateHolder: CharacterListStateHolder = metroViewModel<CharacterListViewModel>(),
) {
  val uiState = stateHolder.produceStateWithLifecycle()

  ReportDrawnWhen { uiState.isSettled }

  CharacterListContent(
    uiState = uiState,
    onAction = stateHolder.accept,
    onNavigateToSettings = onNavigateToSettings,
    reselectEvents = reselectEvents,
  )
}

@Composable
internal fun CharacterListContent(
  uiState: CharacterListUiState,
  onAction: (CharacterListAction) -> Unit,
  onNavigateToSettings: () -> Unit,
  modifier: Modifier = Modifier,
  reselectEvents: Flow<Unit> = emptyFlow(),
) {
  val listState = rememberTiledLazyListState(uiState, Character::id)
  val snackbarHostState = remember { SnackbarHostState() }
  val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

  val currentUiState by rememberUpdatedState(uiState)
  val currentOnAction by rememberUpdatedState(onAction)
  LaunchedEffect(reselectEvents) {
    reselectEvents.collect {
      listState.scrollToFirstPage(currentUiState, Character::id) {
        currentOnAction(CharacterListAction.Tile(it))
      }
    }
  }

  UiMessages(
    messages = uiState.messages,
    snackbarHostState = snackbarHostState,
    onAction = onAction,
    onMessageShown = { onAction(CharacterListAction.MessageShown(it)) },
  )

  HazeScaffold(
    modifier = modifier,
    topBar = {
      CharacterListScreenAppBar(
        onNavigateToSettings = onNavigateToSettings,
        scrollBehavior = scrollBehavior,
      )
    },
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
  ) { contentPadding ->
    TiledList(
      state = uiState,
      itemKey = Character::id,
      onAction = { onAction(CharacterListAction.Tile(it)) },
      modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
      listState = listState,
      contentPadding = contentPadding,
      empty = {
        Empty(
          graphic = {
            Icon(
              painter = painterResource(RamIcons.Outlined.Face),
              contentDescription = null,
              modifier = Modifier.size(64.dp),
            )
          },
          title = { Text(text = stringResource(L10nRes.string.character_list_empty)) },
        )
      },
    ) { character ->
      CharacterItem(character)
    }
  }
}

@Composable
private fun CharacterItem(character: Character) {
  ListItem(
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
        modifier = Modifier.size(64.dp).clip(MaterialTheme.shapes.small),
      )
    },
  ) {
    Text(text = character.name)
  }
}

@Composable
private fun CharacterListScreenAppBar(
  onNavigateToSettings: () -> Unit,
  scrollBehavior: TopAppBarScrollBehavior,
) {
  CenterAlignedTopAppBar(
    title = { Text(text = stringResource(L10nRes.string.character_list_title)) },
    actions = { SettingsNavButton(onClick = onNavigateToSettings) },
    colors =
      TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        scrolledContainerColor = Color.Transparent,
      ),
    scrollBehavior = scrollBehavior,
  )
}

@Preview
@Composable
private fun CharacterItemPreview(
  @PreviewParameter(CharacterPreviewParameterProvider::class) character: Character,
) {
  RamTheme {
    ProvideColorImagePreviewHandler {
      CharacterItem(character = character)
    }
  }
}
