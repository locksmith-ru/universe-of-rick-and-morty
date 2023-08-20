package edu.bedaev.universeofrickandmorty.ui.screen.episodes

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.EpisodeService
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeRemoteKeys
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "_EpisodeViewModel"

@HiltViewModel
class EpisodeViewModel
@Inject constructor(
    private val repository: ListItemRepository,
    private val episodeService: EpisodeService
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        loadingState = AppLoadingState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.fetchItems<EpisodeRemoteKeys, EpisodeEnt>(
                    service = episodeService,
                    keysDao = { db -> db.episodeKeysDao() },
                    listItemDaoFactory = { db -> db.episodesDao() }
                ).map { pagingData ->
                    pagingData.map { Episode(entity = it ) }
                }
            }.fold(
                onSuccess = { flowPagingData ->
                    loadingState =
                        AppLoadingState.Success<Flow<PagingData<Episode>>>(data = flowPagingData)
                },
                onFailure = {
                    Log.e(TAG, "loadContent an error has occurred: ${it.message}", it)
                    loadingState = AppLoadingState.Error(message = it.message ?: "")
                }
            )
        }
    }
}