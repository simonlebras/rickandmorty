package app.rickandmorty.data.location

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.rickandmorty.core.paging.FIRST_PAGE_KEY
import app.rickandmorty.core.paging.PageKeyedRemoteMediator
import app.rickandmorty.core.paging.PageResult
import app.rickandmorty.data.database.TransactionRunner
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity
import app.rickandmorty.data.network.RickAndMortyApi
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@ContributesBinding(AppScope::class)
internal class LocationRepositoryImpl(
  private val api: RickAndMortyApi,
  private val transactionRunner: TransactionRunner,
  private val locationDao: LocationDao,
  private val locationPagedEntryDao: LocationPagedEntryDao,
) : LocationRepository {
  @OptIn(ExperimentalPagingApi::class)
  override fun getPagedLocations(config: PagingConfig): Flow<PagingData<Location>> {
    val remoteMediator =
      PageKeyedRemoteMediator<LocationEntity>(
        pagedEntryResolver = { location ->
          transactionRunner.readTransaction { locationPagedEntryDao.getPagedEntry(location.id) }
        },
        pageFetcher = { page ->
          val (info, results) = api.getLocations(page)

          val resultSize = results.size
          val locations = ArrayList<LocationEntity>(resultSize)
          val pagedEntries = ArrayList<LocationPagedEntryEntity>(resultSize)
          results.forEachIndexed { index, remoteLocation ->
            val location =
              LocationEntity(
                id = remoteLocation.id.toString(),
                name = remoteLocation.name,
                type = remoteLocation.type,
                dimension = remoteLocation.dimension,
              )
            locations.add(location)

            val pagedEntry =
              LocationPagedEntryEntity(
                page = page,
                nextPage = info.nextPage,
                index = index,
                locationId = location.id,
              )
            pagedEntries.add(pagedEntry)
          }
          transactionRunner.writeTransaction {
            if (page == FIRST_PAGE_KEY) {
              locationPagedEntryDao.deleteAll()
            }
            locationDao.insertAll(locations)
            locationPagedEntryDao.insertAll(pagedEntries)
          }

          return@PageKeyedRemoteMediator PageResult(count = info.count, nextPage = info.nextPage)
        },
      )
    return Pager(
        config = config,
        remoteMediator = remoteMediator,
        pagingSourceFactory = { locationDao.getPagedLocations() },
      )
      .flow
      .map { pagingData -> pagingData.map { location -> location.toLocation() } }
  }
}

private fun LocationEntity.toLocation() =
  Location(id = id, name = name, type = type, dimension = dimension)
