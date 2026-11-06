package app.rickandmorty.data.episode

import app.rickandmorty.core.paging.Page
import kotlinx.coroutines.flow.Flow

public interface EpisodeRepository {
  public fun getEpisodePage(page: Int): Flow<Page<Episode>>

  public suspend fun refreshEpisodes()
}
