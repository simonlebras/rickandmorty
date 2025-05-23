package app.rickandmorty.data.episode

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import app.rickandmorty.data.model.Episode
import kotlinx.coroutines.flow.Flow

public interface EpisodeRepository {
  public fun getPagedEpisodes(config: PagingConfig): Flow<PagingData<Episode>>
}
