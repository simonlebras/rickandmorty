package app.rickandmorty.data.location

import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity
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
internal class LocationPageStore(
  private val remoteDataSource: LocationRemoteDataSource,
  persistence: StorePersistence,
  private val locationDao: LocationDao,
  private val locationPagedEntryDao: LocationPagedEntryDao,
) : PageStore<LocationEntity>(name = "location_pages", persistence = persistence) {
  override suspend fun fetch(page: Int): StoredPage<LocationEntity> {
    val (info, results) = remoteDataSource.getLocations(page)
    return StoredPage(
      entities = results.map { it.toLocationEntity() },
      nextPage = info.nextPage(page),
    )
  }

  override fun read(page: Int): Flow<StoredPage<LocationEntity>?> =
    locationDao.getLocationPage(page).map { entries ->
      entries.keys.firstOrNull()?.let { first ->
        StoredPage(entities = entries.values.toList(), nextPage = first.nextPage)
      }
    }

  override suspend fun write(page: Int, stored: StoredPage<LocationEntity>) {
    locationPagedEntryDao.deletePage(page)
    if (stored.nextPage == null) locationPagedEntryDao.deletePagesAfter(page)
    locationDao.upsertAll(stored.entities)
    locationPagedEntryDao.insertAll(
      stored.entities.mapIndexed { index, location ->
        LocationPagedEntryEntity(
          page = page,
          nextPage = stored.nextPage,
          index = index,
          locationId = location.id,
        )
      },
    )
  }

  override suspend fun deletePage(page: Int) {
    locationPagedEntryDao.deletePage(page)
  }

  override suspend fun deleteAll() {
    locationPagedEntryDao.deleteAll()
  }
}

private fun RemoteLocation.toLocationEntity(): LocationEntity =
  LocationEntity(
    id = id.toString(),
    name = name,
    type = type,
    dimension = dimension,
  )
