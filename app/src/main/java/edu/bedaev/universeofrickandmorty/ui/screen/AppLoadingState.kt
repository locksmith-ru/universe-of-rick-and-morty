package edu.bedaev.universeofrickandmorty.ui.screen

sealed interface AppLoadingState{
    data class Success(val data: List<Any> = listOf()): AppLoadingState
    object Error: AppLoadingState
    object Loading: AppLoadingState
}