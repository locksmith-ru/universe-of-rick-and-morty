package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.data.LocationService
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "_LocationViewModel"

@HiltViewModel
class LocationViewModel
@Inject constructor(
    private val repository: ListItemRepository,
    locationService: LocationService
) : BaseViewModel(networkService = locationService) {

    init {
        loadContent()
    }

    override fun loadContent(name: String?) {
        loadingState = AppLoadingState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.fetchItems(
                    service = networkService,
                    keysDao = { db -> db.locationKeysDao() },
                    listItemDaoFactory = { db -> db.locationsDao() },
                    name = name
                ).map { pagingData ->
                    pagingData.map { Location(entity = it) }
                }
            }.fold(
                onSuccess = { flowPagingData ->
                    loadingState =
                        AppLoadingState.Success(data = flowPagingData)
                },
                onFailure = {
                    Log.e(TAG, "loadContent an error has occurred: ${it.message}", it)
                    loadingState = AppLoadingState.Error(message = it.message ?: "")
                }
            )
        }
    }
}