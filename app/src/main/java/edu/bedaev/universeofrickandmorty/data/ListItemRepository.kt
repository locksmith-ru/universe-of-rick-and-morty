package edu.bedaev.universeofrickandmorty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import edu.bedaev.universeofrickandmorty.database.entity.DbEntity
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import kotlinx.coroutines.flow.Flow

class ListItemRepository(
    val database: RickAndMortyDatabase
) {

    @OptIn(ExperimentalPagingApi::class)
    inline fun <reified E: DbEntity> fetchItems(
        service: NetworkService,
        crossinline pagingSource: (RickAndMortyDatabase) -> PagingSource<Int, E>
    ): Flow<PagingData<E>> {
        val config = PagingConfig(pageSize = NetworkService.DEFAULT_PAGE_SIZE)
        val mediator =
            ListItemRemoterMediator<E, ListItem>(
                service = service,
                database = database
            )
        val pager = Pager(
            config = config,
            remoteMediator = mediator,
            pagingSourceFactory = { pagingSource(database) }
        )

        return pager.flow
    }

}