package edu.bedaev.universeofrickandmorty.data

import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.domain.model.Location
import edu.bedaev.universeofrickandmorty.network.api.LocationApi
import javax.inject.Inject

class LocationService @Inject constructor(
    private val api: LocationApi
) : NetworkService{
    override suspend fun fetchData(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): List<ListItem> {
        return api.getLocations(
            page = page,
            filterByName = name
        )
            .locations
            .map { dto ->
                Location(dto = dto)
            }
    }

    override suspend fun fetchSingleData(id: String): List<ListItem> {
        return listOf(Location(dto = api.getSingleLocation(id = id)))
    }

    override suspend fun fetchMultipleData(ids: String): List<ListItem> {
        return api.getMultipleLocations(ids = ids)
            .map { dto -> Location(dto = dto) }
    }

}