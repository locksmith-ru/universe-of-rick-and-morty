package edu.bedaev.universeofrickandmorty.ui.screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import edu.bedaev.universeofrickandmorty.ui.components.SearchWidgetState

abstract class BaseViewModel : ViewModel() {

    var loadingState: AppLoadingState by mutableStateOf(AppLoadingState.Loading)
        protected set

    private val  _searchViewWidgetState : MutableState<SearchWidgetState> =
        mutableStateOf(SearchWidgetState.CLOSED)
    val searchViewWidgetState: State<SearchWidgetState> = _searchViewWidgetState

    private val _searchTextState: MutableState<String> = mutableStateOf("")
    val searchTextState: State<String> = _searchTextState

    abstract fun loadContent()

    fun updateSearchWidgetState(newStateValue: SearchWidgetState){
        _searchViewWidgetState.value = newStateValue
    }

    fun updateSearchTextState(newTextValue: String){
        _searchTextState.value = newTextValue
    }
}