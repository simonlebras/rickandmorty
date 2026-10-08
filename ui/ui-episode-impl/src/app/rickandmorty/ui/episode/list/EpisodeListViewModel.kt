package app.rickandmorty.ui.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.core.navigation.Navigator
import app.rickandmorty.data.episode.Episode
import app.rickandmorty.data.episode.EpisodeRepository
import app.rickandmorty.ui.episode.navigation.EpisodeListNavKey
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow

@ContributesIntoMap(UiScope::class)
@ViewModelKey
internal class EpisodeListViewModel(
  episodeRepository: EpisodeRepository,
  navigator: Navigator,
) : ViewModel() {
  val episodes: Flow<PagingData<Episode>> =
    episodeRepository
      .getPagedEpisodes(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)

  val scrollToTopEvents: Flow<Unit> = navigator.reselectEvents(EpisodeListNavKey)
}
