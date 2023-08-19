package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.CharacterService
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.database.dao.CharacterKeysDao
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repo: ListItemRepository,
    private val characterService: CharacterService
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            delay(1000)
            loadingState = AppLoadingState.Success(
                data = repo.fetchItems<CharacterRemoteKeys, PersonEnt>(
                    service = characterService,
                    keysDao = { db -> db.characterKeysDao() },
                    pagingSource = { db -> db.charactersDao().getEntities() }
                ).map { pagingData ->
                    pagingData.map { Person(entity = it ) }
                }
            )
        }
    }

    /*    @OptIn(ExperimentalPagingApi::class)
        private fun fetchItems(): Flow<PagingData<ListItem>> {
            val config = PagingConfig(pageSize = CharactersApi.DEFAULT_PAGE_SIZE)
            val mediator =
                ListItemRemoterMediator<PersonEnt, Person>(
                    service = service,
                    database = database
                )
            val pager = Pager(
                config = config,
                remoteMediator = mediator,
                pagingSourceFactory = { database.charactersDao().getEntities() }
            )

            return pager.flow.map { pagingData ->
                pagingData.map { entity ->
                    Person(entity = entity)
                }
            }
        }*/
}