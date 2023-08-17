package edu.bedaev.universeofrickandmorty.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import javax.inject.Inject
// todo удалить
class CharacterPagingSource @Inject constructor(
    private val service: CharacterService
) : PagingSource<Int, ListItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ListItem> {
        val page = params.key ?: FIRST_PAGE

        return kotlin.runCatching {
            service.fetchData(page)
        }.fold(
            onSuccess = { list ->
                LoadResult.Page(
                    data = list,
                    prevKey = if (page == FIRST_PAGE) null else page - 1,
                    nextKey = if (list.isEmpty()) null else page + 1
                )
            },
            onFailure = {
                it.printStackTrace()
                LoadResult.Error(it)
            }
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ListItem>): Int? {
        // Try to find the page key of the closest page to anchorPosition from
        // either the prevKey or the nextKey; you need to handle nullability
        // here.
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey are null -> anchorPage is the
        //    initial page, so return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    companion object{
        const val FIRST_PAGE = 1
    }
}