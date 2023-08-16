package edu.bedaev.universeofrickandmorty.ui.screen.locations

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.ui.screen.AppLoadingState
import edu.bedaev.universeofrickandmorty.ui.screen.BaseViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LocationViewModel
@Inject constructor() : BaseViewModel() {

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
                    data = MutableStateFlow(
                        PagingData.from(
                            (1..100).map { Location.fakeLocation() }
                        )
                    )
                )
            } catch (e: IOException) {
                AppLoadingState.Error
            } catch (e: HttpException) {
                AppLoadingState.Error
            }
        }
    }
}