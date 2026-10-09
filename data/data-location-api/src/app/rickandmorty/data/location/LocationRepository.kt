package app.rickandmorty.data.location

import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

public interface LocationRepository {
  public fun getPagedLocations(config: PagingConfig): Flow<PagingData<Location>>
}
