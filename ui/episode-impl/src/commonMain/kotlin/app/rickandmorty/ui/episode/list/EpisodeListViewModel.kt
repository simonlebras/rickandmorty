package app.rickandmorty.ui.episode.list

import androidx.compose.runtime.Stable
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import app.rickandmorty.core.coroutines.inject.DefaultDispatcher
import app.rickandmorty.core.coroutines.viewModelCoroutineScope
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import app.rickandmorty.core.tiling.TilingState
import app.rickandmorty.core.tiling.launchTilingMutations
import app.rickandmorty.core.ui.toUiMessage
import app.rickandmorty.data.episode.Episode
import app.rickandmorty.data.episode.EpisodeRepository
import com.tunjid.mutator.coroutines.ActionSuspendingStateMutator
import com.tunjid.mutator.coroutines.actionSuspendingStateMutator
import com.tunjid.mutator.coroutines.launchMutationsIn
import com.tunjid.mutator.coroutines.launchedCollect
import dev.zacsweers.metro.Assisted
import dev.zacsweers.metro.AssistedFactory
import dev.zacsweers.metro.AssistedInject
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactory
import dev.zacsweers.metrox.viewmodel.ViewModelAssistedFactoryKey
import kotlin.coroutines.CoroutineContext
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

private const val CURRENT_PAGE_KEY = "current_page"

@Stable
public interface EpisodeListStateHolder :
  ActionSuspendingStateMutator<EpisodeListAction, EpisodeListUiState>

@AssistedFactory
@ContributesIntoMap(UiScope::class)
@ViewModelAssistedFactoryKey(EpisodeListViewModel::class)
public fun interface EpisodeListViewModelInitializer : ViewModelAssistedFactory {
  override fun create(extras: CreationExtras): EpisodeListViewModel =
    invoke(scope = viewModelCoroutineScope(), savedStateHandle = extras.createSavedStateHandle())

  public fun invoke(
    @Assisted scope: CoroutineScope,
    @Assisted savedStateHandle: SavedStateHandle,
  ): EpisodeListViewModel
}

public class EpisodeListViewModel(
  mutator: ActionSuspendingStateMutator<EpisodeListAction, EpisodeListUiState>,
  scope: CoroutineScope,
) :
  ViewModel(viewModelScope = scope),
  EpisodeListStateHolder,
  ActionSuspendingStateMutator<EpisodeListAction, EpisodeListUiState> by mutator {
  @AssistedInject
  public constructor(
    episodeRepository: EpisodeRepository,
    @DefaultDispatcher defaultDispatcher: CoroutineContext,
    @Assisted scope: CoroutineScope,
    @Assisted savedStateHandle: SavedStateHandle,
  ) : this(
    mutator =
      scope.actionSuspendingStateMutator(
        state =
          EpisodeListUiState(currentPage = savedStateHandle[CURRENT_PAGE_KEY] ?: FIRST_PAGE_KEY)
            .toSnapshotMutable(),
        producer = { state, actions ->
          val tilingActions = Channel<TilingState.Action>(Channel.UNLIMITED)
          tilingActions
            .receiveAsFlow()
            .launchTilingMutations(
              state = state,
              itemKey = Episode::id,
              pageSource = episodeRepository::getEpisodePage,
              refresh = episodeRepository::refreshEpisodes,
              context = defaultDispatcher,
              onLoadError = { error ->
                state.messages =
                  state.messages
                    .toPersistentList()
                    .adding(error.toUiMessage(EpisodeListAction::Tile))
              },
              onPageChanged = { page -> savedStateHandle[CURRENT_PAGE_KEY] = page },
            )
          actions.launchMutationsIn(productionScope = this, keySelector = EpisodeListAction::key) {
            when (val action = type()) {
              is EpisodeListAction.Tile ->
                action.flow.launchedCollect { tilingActions.send(it.action) }
              is EpisodeListAction.MessageShown ->
                action.flow.launchedCollect {
                  state.messages = state.messages.toPersistentList().removing(it.message)
                }
            }
          }
        },
      ),
    scope = scope,
  )
}
