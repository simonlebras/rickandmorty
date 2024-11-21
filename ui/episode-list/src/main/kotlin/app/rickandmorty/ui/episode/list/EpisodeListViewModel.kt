package app.rickandmorty.ui.episode.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.data.episode.EpisodeRepository
import app.rickandmorty.data.model.Episode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

@HiltViewModel
public class EpisodeListViewModel @Inject constructor(
    episodeRepository: EpisodeRepository,
) : ViewModel() {
    public val episodes: Flow<PagingData<Episode>> = episodeRepository
        .getPagedEpisodes(
            config = PagingConfig(pageSize = 24),
        )
        .cachedIn(viewModelScope)
}
