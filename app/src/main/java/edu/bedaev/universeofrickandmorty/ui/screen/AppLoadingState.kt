package edu.bedaev.universeofrickandmorty.ui.screen

import edu.bedaev.universeofrickandmorty.domain.model.ListItem

sealed interface AppLoadingState{
    data class Success(val data: List<Any> = listOf()): AppLoadingState
    data object Error: AppLoadingState
    data object Loading: AppLoadingState
}