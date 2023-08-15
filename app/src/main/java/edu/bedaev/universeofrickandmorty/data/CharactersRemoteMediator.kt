package edu.bedaev.universeofrickandmorty.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import edu.bedaev.universeofrickandmorty.database.model.PersonEnt
import edu.bedaev.universeofrickandmorty.database.model.RemoteKeys
import edu.bedaev.universeofrickandmorty.domain.model.Person
import okio.IOException
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class CharactersRemoteMediator(
    private val characterService: CharacterService,
    private val database: RickAndMortyDatabase
) : RemoteMediator<Int, PersonEnt>(){

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (database.remoteKeysDao().getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PersonEnt>
    ): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey
                prevKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                nextKey ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            // todo добавить фильтры name, species, gender etc.
            val persons: List<Person> = characterService.fetchData(page)
                .map { item -> item as Person }


            val endOfPaginationReached: Boolean = persons.isEmpty()

            database.withTransaction {
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1

                val remoteKeys = persons.map { listItem ->
                    RemoteKeys(
                        listItemId = listItem.id,
                        prevKey = prevKey,
                        currentPage = page,
                        nextKey = nextKey
                    )
                }
                val entityList = persons.map{ PersonEnt(person = it, page = page) }
                if (loadType == LoadType.REFRESH){
                    database.remoteKeysDao().refresh(remoteKeys)
                    database.charactersDao().refresh(entityList)
                }else{
                    database.remoteKeysDao().saveAll(keys = remoteKeys)
                    database.charactersDao().saveAll(characters = entityList)
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (error: IOException) {
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            return MediatorResult.Error(error)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, PersonEnt>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                database.remoteKeysDao().getKeyByListItemId(listItemId = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, PersonEnt>): RemoteKeys? {
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { listItem ->
            database.remoteKeysDao().getKeyByListItemId(listItemId = listItem.id)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, PersonEnt>): RemoteKeys? {
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { listItem ->
            database.remoteKeysDao().getKeyByListItemId(listItemId = listItem.id)
        }
    }
}