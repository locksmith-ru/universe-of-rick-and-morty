package edu.bedaev.universeofrickandmorty.ui.screen.characters

import androidx.lifecycle.viewModelScope
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.network.model.Location
import edu.bedaev.universeofrickandmorty.network.model.Origin
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
                AppLoadingState.Success(
                    data = (1..100)
                        .map { i ->
                            val status = if (i % 2 == 0) "Alive"
                            else if (i % 3 == 0) "Dead"
                            else "unknown"

                            Person(
                                id = i, name = "Person name $i", status = status, species = "Human",
                                type = null, gender = "Male",
                                origin = Origin(
                                    name = "Earth (C-137)",
                                    url = "https://rickandmortyapi.com/api/location/1"
                                ),
                                location = Location(
                                    name = "Citadel of Ricks",
                                    url = "https://rickandmortyapi.com/api/location/3"
                                ),
                                image = null, episodeList = emptyList(), url = null, created = null
                            )
                        }
                )
            } catch (e: IOException) {
                AppLoadingState.Error
            } catch (e: HttpException) {
                AppLoadingState.Error
            }
        }
    }

}