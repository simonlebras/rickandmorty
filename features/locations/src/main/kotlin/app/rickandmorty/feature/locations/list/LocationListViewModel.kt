package app.rickandmorty.feature.locations.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import app.rickandmorty.data.location.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class LocationListViewModel @Inject constructor(
    locationRepository: LocationRepository,
) : ViewModel() {
    val locations = locationRepository
        .getPagedLocations(
            config = PagingConfig(pageSize = 24),
        )
        .cachedIn(viewModelScope)
}
