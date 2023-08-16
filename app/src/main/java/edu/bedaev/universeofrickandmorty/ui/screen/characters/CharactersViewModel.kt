package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.CharacterPagingSource
import edu.bedaev.universeofrickandmorty.data.CharacterRepository
import edu.bedaev.universeofrickandmorty.data.CharacterService
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repository: CharacterRepository,
    private val service: CharacterService
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            loadingState = try {
                // todo Заменить на RemoterMediator
                AppLoadingState.Success(
                    data = Pager(
                        config = PagingConfig(
                            pageSize = 20,
                            enablePlaceholders = true,
                            maxSize = 200),
                    ){ CharacterPagingSource(service = service) }
                        .flow.cachedIn(viewModelScope)
                )
            } catch (e: IOException) {
                AppLoadingState.Error
            } catch (e: HttpException) {
                AppLoadingState.Error
            }
        }
    }
}