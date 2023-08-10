package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class CharactersViewModel : ViewModel() {

    var loadingState: AppLoadingState by mutableStateOf(AppLoadingState.Loading)
        private set

    init {
        loadCharacters()
    }

    fun loadCharacters(){
        viewModelScope.launch {
            loadingState = AppLoadingState.Loading
            // иммитация загрузки
            delay(3000)
            loadingState = try {
                // todo Здесь загрузка данных из сети
                AppLoadingState.Success(data = List(100) { i -> "Item_${i}" })
            }catch (e: IOException){
                AppLoadingState.Error
            } catch (e: HttpException){
                AppLoadingState.Error
            }
        }
    }

}