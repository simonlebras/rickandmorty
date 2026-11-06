package app.rickandmorty.data.episode

import app.rickandmorty.data.database.dao.EpisodeDao
import app.rickandmorty.data.database.dao.EpisodePagedEntryDao
import app.rickandmorty.data.database.entity.EpisodeEntity
import app.rickandmorty.data.database.entity.EpisodePagedEntryEntity
import app.rickandmorty.data.rest.nextPage
import app.rickandmorty.data.store.PageStore
import app.rickandmorty.data.store.StorePersistence
import app.rickandmorty.data.store.StoredPage
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Inject
@SingleIn(AppScope::class)
internal class EpisodePageStore(
  private val remoteDataSource: EpisodeRemoteDataSource,
  persistence: StorePersistence,
  private val episodeDao: EpisodeDao,
  private val episodePagedEntryDao: EpisodePagedEntryDao,
) : PageStore<EpisodeEntity>(name = "episode_pages", persistence = persistence) {
  override suspend fun fetch(page: Int): StoredPage<EpisodeEntity> {
    val (info, results) = remoteDataSource.getEpisodes(page)
    return StoredPage(
      entities = results.map { it.toEpisodeEntity() },
      nextPage = info.nextPage(page),
    )
  }

  override fun read(page: Int): Flow<StoredPage<EpisodeEntity>?> =
    episodeDao.getEpisodePage(page).map { entries ->
      entries.keys.firstOrNull()?.let { first ->
        StoredPage(entities = entries.values.toList(), nextPage = first.nextPage)
      }
    }

  override suspend fun write(page: Int, stored: StoredPage<EpisodeEntity>) {
    episodePagedEntryDao.deletePage(page)
    if (stored.nextPage == null) episodePagedEntryDao.deletePagesAfter(page)
    episodeDao.upsertAll(stored.entities)
    episodePagedEntryDao.insertAll(
      stored.entities.mapIndexed { index, episode ->
        EpisodePagedEntryEntity(
          page = page,
          nextPage = stored.nextPage,
          index = index,
          episodeId = episode.id,
        )
      },
    )
  }

  override suspend fun deletePage(page: Int) {
    episodePagedEntryDao.deletePage(page)
  }

  override suspend fun deleteAll() {
    episodePagedEntryDao.deleteAll()
  }
}

private fun RemoteEpisode.toEpisodeEntity(): EpisodeEntity =
  EpisodeEntity(
    id = id.toString(),
    name = name,
    airDate = airDate,
    episode = episode,
  )
