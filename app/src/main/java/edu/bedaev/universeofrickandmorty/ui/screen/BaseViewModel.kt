package edu.bedaev.universeofrickandmorty.ui.screen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bedaev.universeofrickandmorty.data.NetworkService
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.ui.components.SearchWidgetState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "_BaseViewModel"

abstract class BaseViewModel(
    protected val networkService: NetworkService
) : ViewModel() {

    var loadingState: AppLoadingState by mutableStateOf(AppLoadingState.Loading)
        protected set

    private val _searchViewWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)
    val searchViewWidgetState: State<SearchWidgetState> = _searchViewWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    private val listItemChannel: Channel<List<ListItem>> = Channel()
    val multipleListItemFlow: Flow<List<ListItem>> = listItemChannel.receiveAsFlow()

    abstract fun loadContent(name: String? = null)

    fun updateSearchWidgetState(newStateValue: SearchWidgetState) {
        _searchViewWidgetState.value = newStateValue
    }

    fun updateSearchTextState(newTextValue: String) {
        _searchTextState.value = newTextValue
    }

    /**
     * Загрузка элементов по заданным id
     */
    fun loadMultipleItems(urlList: List<String>) {
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
                    networkService.fetchSingleData(id = ids)
                else
                    networkService.fetchMultipleData(ids = ids)
            }.fold(
                onSuccess = { list ->
                    listItemChannel.trySendBlocking(list)
                },
                onFailure = {
                    Log.e(TAG, "loadMultipleItems error occurred: ${it.message}", it)
                    listItemChannel.trySendBlocking(emptyList())
                }
            )
        }
    }
}