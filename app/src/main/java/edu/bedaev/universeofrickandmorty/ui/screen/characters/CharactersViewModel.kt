package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.data.CharacterRepository
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel
@Inject constructor(
    private val repository: CharacterRepository
) : BaseViewModel() {

    init {
        loadContent()
    }

    override fun loadContent() {
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            delay(1000)
            loadingState = try {
                AppLoadingState.Success(
                    data = repository.getCharacters()
                )
            } catch (e: IOException) {
                AppLoadingState.Error
            } catch (e: HttpException) {
                AppLoadingState.Error
            }
        }
    }
}