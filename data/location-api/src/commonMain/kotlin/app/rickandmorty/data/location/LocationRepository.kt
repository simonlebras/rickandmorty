package app.rickandmorty.data.location

import app.rickandmorty.core.paging.Page
import kotlinx.coroutines.flow.Flow

public interface LocationRepository {
  public fun getLocationPage(page: Int): Flow<Page<Location>>

  public suspend fun refreshLocations()
}
