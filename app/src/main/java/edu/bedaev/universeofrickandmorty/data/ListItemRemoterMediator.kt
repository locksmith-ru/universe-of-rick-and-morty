package edu.bedaev.universeofrickandmorty.data

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import edu.bedaev.universeofrickandmorty.database.RickAndMortyDatabase
import edu.bedaev.universeofrickandmorty.database.dao.BaseDao
import edu.bedaev.universeofrickandmorty.database.dao.BaseKeyDao
import edu.bedaev.universeofrickandmorty.database.dao.CharacterKeysDao
import edu.bedaev.universeofrickandmorty.database.dao.LocationKeysDao
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.DbEntity
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt
import edu.bedaev.universeofrickandmorty.database.entity.LocationRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt
import edu.bedaev.universeofrickandmorty.database.entity.RemoteKey
import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.domain.model.Person
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class ListItemRemoterMediator<K : RemoteKey, E : DbEntity, L : ListItem>(
    private val service: NetworkService,
    private val database: RickAndMortyDatabase,
    private val listItemDao: BaseDao<E>,
    private val keysDao: BaseKeyDao<K>,
    private val name: String? = null
) : RemoteMediator<Int, E>() {

    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)
        val elapsedTime: Long = System.currentTimeMillis() - (keysDao.getCreationTime() ?: 0)
        val isEqualsQuery: Boolean = keysDao.getPreviousQuery() == name

        return if (elapsedTime < cacheTimeout && isEqualsQuery) {
            // кешированные данные актуальны
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            // кэшированные данные просрочены и требуется обновление
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, E>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                // новый запрос
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: 1
            }

            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                // Если значение remoteKeys равно null, это означает,
                // что результата обновления еще нет в базе данных.
                val prevKey = remoteKeys?.prevKey
                prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }

            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                // Если значение remoteKeys равно null, это означает,
                // что результата обновления еще нет в базе данных.
                // Мы можем вернуть Success с endOfPaginationReached = false, потому что подкачка
                // по страницам вызовет этот метод снова, если значение удаленных ключей станет ненулевым.
                // Если значение remote Keys не равно NULL, но его следующий ключ равен null,
                // это означает, что мы достигли конца разбивки на страницы для добавления.
                val nextKey = remoteKeys?.nextKey
                nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
            }
        }

        return try {
            val items: List<L> = service.fetchData(page = page, name = name)
                .map { listItem -> listItem as L }
            val endOfPaginationReached = items.isEmpty()

            database.withTransaction {
                val prevKey = if (page > 1) page - 1 else null
                val nextKey = if (endOfPaginationReached) null else page + 1
                val remoteKeys = items.map { listItem ->
                    getRemoteKey(
                        listItem = listItem, prevKey = prevKey,
                        page = page, nextKey = nextKey
                    )
                }
                val entities: List<E> = items.map { klass ->
                    when (klass) {
                        is Person -> PersonEnt(person = klass, page = page) as E
                        is Location -> LocationEnt(location = klass, page = page) as E
                        is Episode -> EpisodeEnt(model = klass, page = page) as E
                        else -> error("unreachable")
                    }
                }

                if (loadType == LoadType.REFRESH) {
                    keysDao.deleteAll()
                    keysDao.saveAll(keys = remoteKeys as List<K>)
                    listItemDao.deleteAll()
                    listItemDao.saveAll(list = entities)
                } else {
                    keysDao.saveAll(keys = remoteKeys as List<K>)
                    listItemDao.saveAll(list = entities)
                }
            }

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (error: IOException) {
            Log.e("_RemoterMediator", "load error: ${error.message}", error)
            return MediatorResult.Error(error)
        } catch (error: HttpException) {
            Log.e("_RemoterMediator", "load error: ${error.message}", error)
            return MediatorResult.Error(error)
        }
    }

    private fun getRemoteKey(
        listItem: L,
        prevKey: Int?,
        page: Int,
        nextKey: Int?
    ) = when (keysDao) {
        is CharacterKeysDao -> CharacterRemoteKeys(
            listItemId = listItem.id,
            prevKey = prevKey,
            currentPage = page,
            nextKey = nextKey,
            prevQuery = name
        )

        is LocationKeysDao -> LocationRemoteKeys(
            listItemId = listItem.id,
            prevKey = prevKey,
            currentPage = page,
            nextKey = nextKey,
            prevQuery = name
        )

        else -> EpisodeRemoteKeys(
            listItemId = listItem.id,
            prevKey = prevKey,
            currentPage = page,
            nextKey = nextKey,
            prevQuery = name
        )
    }

    /**
     * Вызывается при первой загрузке данных или при вызове функции Paging Data Adapter.refresh();
     * итак, теперь точкой отсчета для загрузки ваших данных является положение state.anchor.
     * Если это первая загрузка, то положение привязки равно null.
     * При вызове функции Paging Data Adapter.refresh()
     * позиция привязки является первой видимой позицией в отображаемом списке,
     * поэтому нам нужно будет загрузить страницу, содержащую этот конкретный элемент.
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, E>): K? {
        //Paging library пытается загрузить данные после позиции привязки
        //Найдите элемент, расположенный ближе всего к позиции привязки
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                keysDao.getKeyByListItemId(listItemId = id)
            }
        }
    }

    /**
     * Когда нам нужно загрузить данные в начале текущего загруженного списка данных,
     * параметром загрузки является LoadType.PREPEND
     */
    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, E>): K? {
        // Получите первую полученную страницу, которая содержала элементы.
        // С этой первой страницы получите первый элемент
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { entity ->
            keysDao.getKeyByListItemId(listItemId = entity.id)
        }
    }

    /**
     * Когда нам нужно загрузить данные в конце текущего загруженного набора данных,
     * параметром загрузки является LoadType.APPEND
     */
    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, E>): K? {
        // Получите последнюю извлеченную страницу, которая содержала элементы.
        //С этой последней страницы получите последний элемент
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { entity ->
            keysDao.getKeyByListItemId(listItemId = entity.id)
        }
    }
}
