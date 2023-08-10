package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException

class CharactersViewModel : BaseViewModel() {

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
                AppLoadingState.Success(data = List(100) { i -> "Character_${i}" })
            }catch (e: IOException){
                AppLoadingState.Error
            } catch (e: HttpException){
                AppLoadingState.Error
            }
        }
    }

}