package app.rickandmorty.data.location

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import app.rickandmorty.data.database.TransactionRunner
import app.rickandmorty.data.database.dao.LocationDao
import app.rickandmorty.data.database.dao.LocationPagedEntryDao
import app.rickandmorty.data.database.entity.LocationEntity
import app.rickandmorty.data.database.entity.LocationPagedEntryEntity
import app.rickandmorty.data.database.entity.toLocation
import app.rickandmorty.data.model.Location
import app.rickandmorty.data.paging.FIRST_PAGE_KEY
import app.rickandmorty.data.paging.PageKeyedRemoteMediator
import app.rickandmorty.data.paging.PageResult
import com.apollographql.apollo.ApolloClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LocationRepositoryImpl(
    private val apolloClient: ApolloClient,
    private val transactionRunner: TransactionRunner,
    private val locationDao: LocationDao,
    private val locationPagedEntryDao: LocationPagedEntryDao,
) : LocationRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedLocations(config: PagingConfig): Flow<PagingData<Location>> {
        val remoteMediator = PageKeyedRemoteMediator<LocationEntity>(
            pagedEntryResolver = { location ->
                transactionRunner {
                    locationPagedEntryDao.getPagedEntry(location.id)
                }
            },
            pageFetcher = { page ->
                val data = apolloClient
                    .query(GetLocationsQuery(page = page))
                    .execute()
                    .dataAssertNoErrors

                val (info, results) = data.locations!!

                val resultSize = results.size
                val locations = ArrayList<LocationEntity>(resultSize)
                val pagedEntries = ArrayList<LocationPagedEntryEntity>(resultSize)
                results.forEachIndexed { index, remoteLocation ->
                    val location = LocationEntity(
                        id = remoteLocation.id,
                        name = remoteLocation.name,
                        type = remoteLocation.type,
                        dimension = remoteLocation.dimension,
                    )
                    locations.add(location)

                    val pagedEntry = LocationPagedEntryEntity(
                        page = page,
                        nextPage = info.next,
                        index = index,
                        locationId = location.id,
                    )
                    pagedEntries.add(pagedEntry)
                }
                transactionRunner {
                    if (page == FIRST_PAGE_KEY) {
                        locationPagedEntryDao.deleteAll()
                    }
                    locationDao.insertAll(locations)
                    locationPagedEntryDao.insertAll(pagedEntries)
                }

                return@PageKeyedRemoteMediator PageResult(
                    count = info.count,
                    nextPage = info.next,
                )
            },
        )
        return Pager(
            config = config,
            remoteMediator = remoteMediator,
            pagingSourceFactory = { locationDao.getPagedLocations() },
        )
            .flow
            .map { pagingData ->
                pagingData.map { location ->
                    location.toLocation()
                }
            }
    }
}
