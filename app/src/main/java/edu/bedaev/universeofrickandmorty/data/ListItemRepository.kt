package edu.bedaev.universeofrickandmorty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import edu.bedaev.universeofrickandmorty.database.dao.BaseDao
import edu.bedaev.universeofrickandmorty.database.dao.BaseKeyDao
import edu.bedaev.universeofrickandmorty.database.entity.DbEntity
import edu.bedaev.universeofrickandmorty.database.entity.RemoteKey
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

class ListItemRepository(
    val database: RickAndMortyDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    inline fun <reified K : RemoteKey, reified E : DbEntity> fetchItems(
        service: NetworkService,
        crossinline listItemDaoFactory: (RickAndMortyDatabase) -> BaseDao<E>,
        keysDao: (RickAndMortyDatabase) -> BaseKeyDao<K>,
        name: String? = null
    ): Flow<PagingData<E>> {
        val config = PagingConfig(pageSize = NetworkService.DEFAULT_PAGE_SIZE)
        val mediator =
            ListItemRemoterMediator<K, E, ListItem>(
                service = service,
                database = database,
                listItemDao = listItemDaoFactory(database),
                keysDao = keysDao(database),
                name = name
            )
        val pager = Pager(
            config = config,
            remoteMediator = mediator,
            pagingSourceFactory = { listItemDaoFactory(database).getEntities() }
        )

        return pager.flow
    }

}