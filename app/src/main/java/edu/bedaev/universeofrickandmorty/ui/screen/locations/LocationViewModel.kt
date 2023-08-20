package edu.bedaev.universeofrickandmorty.ui.screen.locations

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.data.LocationService
import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt
import edu.bedaev.universeofrickandmorty.database.entity.LocationRemoteKeys
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "_LocationViewModel"

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
        loadingState = AppLoadingState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.fetchItems<LocationRemoteKeys, LocationEnt>(
                    service = locationService,
                    keysDao = { db -> db.locationKeysDao() },
                    listItemDaoFactory = { db -> db.locationsDao() }
                ).map { pagingData ->
                    pagingData.map { Location(entity = it ) }
                }
            }.fold(
                onSuccess = { flowPagingData ->
                    loadingState =
                        AppLoadingState.Success<Flow<PagingData<Location>>>(data = flowPagingData)
                },
                onFailure = {
                    Log.e(TAG, "loadContent an error has occurred: ${it.message}", it)
                    loadingState = AppLoadingState.Error(message = it.message ?: "")
                }
            )
        }
    }
}