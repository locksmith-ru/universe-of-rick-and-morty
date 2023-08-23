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
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "_EpisodeViewModel"

@HiltViewModel
class EpisodeViewModel
@Inject constructor(
    private val repository: ListItemRepository,
    private val episodeService: EpisodeService
) : BaseViewModel() {

    private val multipleEpisodesChannel: Channel<List<ListItem>> = Channel()
    val multipleEpisodesFlow: Flow<List<ListItem>> = multipleEpisodesChannel.receiveAsFlow()

    init {
        loadContent()
    }

    override fun loadContent(name: String?) {
        loadingState = AppLoadingState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repository.fetchItems<EpisodeRemoteKeys, EpisodeEnt>(
                    service = episodeService,
                    keysDao = { db -> db.episodeKeysDao() },
                    listItemDaoFactory = { db -> db.episodesDao() },
                    name = name,
                ).map { pagingData ->
                    pagingData.map { Episode(entity = it) }
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

    override fun loadMultipleItems(urlList: List<String>) {
        viewModelScope.launch {

            val ids = if (urlList.size == 1) {
                val url = urlList.first()
                url.substring(url.lastIndexOf("/") + 1, url.length)
            } else {
                urlList.joinToString(separator = ",") { url ->
                    url.substring(url.lastIndexOf("/") + 1, url.length)
                }
            }
            kotlin.runCatching {
                if (urlList.size == 1)
                    episodeService.fetchSingleData(id = ids)
                else
                    episodeService.fetchMultipleData(ids = ids)
            }.fold(
                onSuccess = { list ->
                    multipleEpisodesChannel.trySendBlocking(list)
                },
                onFailure = {
                    Log.e(TAG, "loadMultipleItems error occurred: ${it.message}", it)
                    multipleEpisodesChannel.trySendBlocking(emptyList())
                }
            )
        }
    }
}