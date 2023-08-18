package edu.bedaev.universeofrickandmorty.ui.screen.locations

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.data.LocationService
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
@Inject constructor(
    private val repository: ListItemRepository,
    private val locationService: LocationService
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            // иммитация загрузки
            delay(1000)
            loadingState = AppLoadingState.Success(
                data = repository.fetchItems(service = locationService)
                { database ->
                    database.locationsDao().getEntities()
                }.map { pagingData ->
                    pagingData.map { Location(entity = it) }
                }
            )

        }
    }
}