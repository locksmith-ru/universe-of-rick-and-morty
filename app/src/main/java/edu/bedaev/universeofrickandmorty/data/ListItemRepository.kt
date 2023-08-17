package edu.bedaev.universeofrickandmorty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import edu.bedaev.universeofrickandmorty.database.model.PersonEnt
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.network.api.CharactersApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ListItemRepository(
    private val service: NetworkService,
    private val database: RickAndMortyDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    fun fetchItems(): Flow<PagingData<ListItem>> {
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
    }

}