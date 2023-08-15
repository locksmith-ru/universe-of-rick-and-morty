package edu.bedaev.universeofrickandmorty.ui.screen

import androidx.paging.PagingData
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import kotlinx.coroutines.flow.Flow

sealed interface AppLoadingState{
//    data class Success(val data: List<ListItem> = listOf()): AppLoadingState
    data class  Success(val data: Flow<PagingData<ListItem>>): AppLoadingState
    data object Error: AppLoadingState
    data object Loading: AppLoadingState
}