package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.CharacterRepository
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repository: CharacterRepository
) : BaseViewModel() {

    val listItemFlow: Flow<PagingData<ListItem>> =
        repository.getCharacters()
            .cachedIn(CoroutineScope(Dispatchers.Default))

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            // иммитация загрузки
            delay(3000)
            loadingState = try {
                // todo Здесь загрузка данных из сети
                AppLoadingState.Success(
                    data = (1..100).map {
                        Person.fakePerson()
                    }
                )
            } catch (e: IOException) {
                AppLoadingState.Error
            } catch (e: HttpException) {
                AppLoadingState.Error
            }
        }
    }

    override fun loadPagingData() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            kotlin.runCatching {

            }.fold(
                onSuccess = {},
                onFailure = {}
            )
        }
    }
}