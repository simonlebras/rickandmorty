package app.rickandmorty.data.location

import app.rickandmorty.core.paging.Page
import app.rickandmorty.data.database.entity.LocationEntity
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import kotlinx.coroutines.flow.Flow

@ContributesBinding(AppScope::class)
internal class LocationRepositoryImpl(private val pages: LocationPageStore) : LocationRepository {
  override fun getLocationPage(page: Int): Flow<Page<Location>> =
    pages.page(page) { it.toLocation() }

  override suspend fun refreshLocations() {
    pages.invalidateAll()
  }
}

private fun LocationEntity.toLocation(): Location =
  Location(id = id, name = name, type = type, dimension = dimension)
