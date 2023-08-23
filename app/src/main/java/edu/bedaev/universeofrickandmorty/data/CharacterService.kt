package edu.bedaev.universeofrickandmorty.data

import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.network.api.CharactersApi
import javax.inject.Inject

class CharacterService @Inject constructor(
    private val api: CharactersApi
) : NetworkService {
    override suspend fun fetchData(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): List<ListItem> {
        return api.getCharacters(
            page = page,
            filterByName = name,
            filterByStatus = status,
            filterBySpecies = species,
            filterByGender = gender
        )
            .persons
            .map { dto ->
                Person(dto)
            }
    }

    override suspend fun fetchSingleData(id: String): List<ListItem> {
        return listOf(Person(dto = api.getSingleCharacter(id = id)))
    }

    override suspend fun fetchMultipleData(ids: String): List<ListItem> {
        return api.getMultipleCharacters(ids = ids)
            .map { dto -> Person(dto = dto) }
    }
}