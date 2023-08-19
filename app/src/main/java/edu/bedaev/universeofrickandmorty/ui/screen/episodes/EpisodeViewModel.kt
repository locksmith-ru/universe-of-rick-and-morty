package edu.bedaev.universeofrickandmorty.ui.screen.episodes

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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

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
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            delay(1000)
            loadingState = AppLoadingState.Success(
                data = repository
                    .fetchItems(
                        service = episodeService,
                        keysDao = { db -> db.episodeKeysDao() },
                        listItemDaoFactory = { db -> db.episodesDao() },
                        pagingSource = { db ->
                            db.episodesDao().getEntities()
                        }
                    ) .map { pagingData ->
                        pagingData.map { Episode(entity = it) }
                    }
            )
        }
    }
}