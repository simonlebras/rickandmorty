package app.rickandmorty.data.episode

import app.rickandmorty.core.paging.Page
import app.rickandmorty.data.database.entity.EpisodeEntity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
internal class EpisodeRepositoryImpl(private val pages: EpisodePageStore) : EpisodeRepository {
  override fun getEpisodePage(page: Int): Flow<Page<Episode>> = pages.page(page) { it.toEpisode() }

  override suspend fun refreshEpisodes() {
    pages.invalidateAll()
  }
}

private fun EpisodeEntity.toEpisode(): Episode =
  Episode(id = id, name = name, airDate = airDate, episode = episode)
