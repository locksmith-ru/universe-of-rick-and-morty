package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repo: ListItemRepository
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            delay(1000)
            loadingState = AppLoadingState.Success(
                data = repo.fetchItems()
            )
        }
    }
}

/*
    @OptIn(ExperimentalPagingApi::class)
    override fun loadContent() {
        viewModelScope.launch {
            val config = PagingConfig(pageSize = CharactersApi.DEFAULT_PAGE_SIZE)
            val mediator =
                ListItemRemoterMediator<PersonEnt, Person>(
                    service = characterService,
                    database = database
                )
            val pager = Pager(
                config = config,
                remoteMediator = mediator,
                pagingSourceFactory = { database.charactersDao().getEntities() }
            )
            loadingState = AppLoadingState.Loading
            delay(1000)
            loadingState = AppLoadingState.Success(
                data = pager.flow.map { pagingData ->
                    pagingData.map { entity -> Person(entity = entity) }
                }
            )
        }
    }
 */