package app.rickandmorty.ui.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.core.metro.ViewModelKey
import app.rickandmorty.core.metro.ViewModelScope
import app.rickandmorty.data.location.Location
import app.rickandmorty.data.location.LocationRepository
import dev.zacsweers.metro.ContributesIntoMap
import kotlinx.coroutines.flow.Flow

@ContributesIntoMap(ViewModelScope::class)
@ViewModelKey(LocationListViewModel::class)
public class LocationListViewModel(locationRepository: LocationRepository) : ViewModel() {
  public val locations: Flow<PagingData<Location>> =
    locationRepository
      .getPagedLocations(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)
}
