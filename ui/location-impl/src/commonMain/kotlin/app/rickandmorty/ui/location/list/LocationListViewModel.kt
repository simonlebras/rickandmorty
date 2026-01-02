package app.rickandmorty.ui.location.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import app.rickandmorty.core.metro.UiScope
import app.rickandmorty.data.location.Location
import app.rickandmorty.data.location.LocationRepository
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import kotlinx.coroutines.flow.Flow

@ContributesIntoMap(UiScope::class)
@ViewModelKey(LocationListViewModel::class)
public class LocationListViewModel(locationRepository: LocationRepository) : ViewModel() {
  public val locations: Flow<PagingData<Location>> =
    locationRepository
      .getPagedLocations(config = PagingConfig(pageSize = 24))
      .cachedIn(viewModelScope)
}
