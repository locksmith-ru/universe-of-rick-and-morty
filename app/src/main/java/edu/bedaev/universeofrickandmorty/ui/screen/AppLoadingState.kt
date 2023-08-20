package edu.bedaev.universeofrickandmorty.ui.screen

sealed interface AppLoadingState{
    data class  Success<T>(val data: T? = null): AppLoadingState
    data class Error(val message: String = ""): AppLoadingState
    data object Loading: AppLoadingState
}