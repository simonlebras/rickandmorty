package app.rickandmorty.feature.episodes.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.rickandmorty.data.episode.EpisodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class EpisodeListViewModel @Inject constructor(
    episodeRepository: EpisodeRepository,
) : ViewModel() {
    val episodes = episodeRepository
        .getPagedEpisodes(
            config = PagingConfig(pageSize = 24),
        )
        .cachedIn(viewModelScope)
}
