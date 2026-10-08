package app.rickandmorty.data.episode

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import app.rickandmorty.core.paging.PageKeyedRemoteMediator
import app.rickandmorty.core.paging.PageResult
import app.rickandmorty.data.database.TransactionRunner
import app.rickandmorty.data.database.dao.EpisodeDao
import app.rickandmorty.data.database.dao.EpisodePagedEntryDao
import app.rickandmorty.data.database.entity.EpisodeEntity
import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity
import app.rickandmorty.data.network.RickAndMortyApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ContributesBinding(AppScope::class)
internal class EpisodeRepositoryImpl(
  private val api: RickAndMortyApi,
  private val transactionRunner: TransactionRunner,
  private val episodeDao: EpisodeDao,
  private val episodePagedEntryDao: EpisodePagedEntryDao,
) : EpisodeRepository {
  @OptIn(ExperimentalPagingApi::class)
  override fun getPagedEpisodes(config: PagingConfig): Flow<PagingData<Episode>> {
    val remoteMediator =
      PageKeyedRemoteMediator<EpisodeEntity>(
        pagedEntryResolver = { episode ->
          transactionRunner.readTransaction { episodePagedEntryDao.getPagedEntry(episode.id) }
        },
        pageFetcher = { page ->
          val (info, results) = api.getEpisodes(page)

          val resultSize = results.size
          val episodes = ArrayList<EpisodeEntity>(resultSize)
          val pagedEntries = ArrayList<EpisodePagedEntryEntity>(resultSize)
          results.forEachIndexed { index, remoteEpisode ->
            val episode =
              EpisodeEntity(
                id = remoteEpisode.id.toString(),
                name = remoteEpisode.name,
                airDate = remoteEpisode.airDate,
                episode = remoteEpisode.episode,
              )
            episodes.add(episode)

            val pagedEntry =
              EpisodePagedEntryEntity(
                page = page,
                nextPage = info.nextPage,
                index = index,
                episodeId = episode.id,
              )
            pagedEntries.add(pagedEntry)
          }
          transactionRunner.writeTransaction {
            if (page == FIRST_PAGE_KEY) {
              episodePagedEntryDao.deleteAll()
            }
            episodeDao.insertAll(episodes)
            episodePagedEntryDao.insertAll(pagedEntries)
          }

          return@PageKeyedRemoteMediator PageResult(count = info.count, nextPage = info.nextPage)
        },
      )
    return Pager(
        config = config,
        remoteMediator = remoteMediator,
        pagingSourceFactory = { episodeDao.getPagedEpisodes() },
      )
      .flow
      .map { pagingData -> pagingData.map { episode -> episode.toEpisode() } }
  }
}

private fun EpisodeEntity.toEpisode() =
  Episode(id = id, name = name, airDate = airDate, episode = episode)
