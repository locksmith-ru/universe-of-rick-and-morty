package edu.bedaev.universeofrickandmorty.ui.screen.characters

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.CharacterService
import edu.bedaev.universeofrickandmorty.data.ListItemRepository
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "_CharactersViewModel"

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repo: ListItemRepository,
    characterService: CharacterService
) : BaseViewModel(networkService = characterService) {

    init {
        loadContent()
    }

    override fun loadContent(name: String?) {
        loadingState = AppLoadingState.Loading
        viewModelScope.launch {
            kotlin.runCatching {
                repo.fetchItems<CharacterRemoteKeys, PersonEnt>(
                    service = networkService,
                    keysDao = { db -> db.characterKeysDao() },
                    listItemDaoFactory = { db -> db.charactersDao() },
                    name = name
                ).map { pagingData ->
                    pagingData.map { Person(entity = it) }
                }
            }.fold(
                onSuccess = { flowPagingData ->
                    loadingState =
                        AppLoadingState.Success<Flow<PagingData<Person>>>(data = flowPagingData)
                },
                onFailure = {
                    Log.e(TAG, "loadContent an error has occurred: ${it.message}", it)
                    loadingState = AppLoadingState.Error(message = it.message ?: "")
                }
            )
        }
    }
}