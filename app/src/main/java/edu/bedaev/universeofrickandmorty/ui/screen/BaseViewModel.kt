package edu.bedaev.universeofrickandmorty.ui.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    var loadingState: AppLoadingState by mutableStateOf(AppLoadingState.Loading)
        protected set

    abstract fun loadContent()

    abstract fun loadPagingData()
}