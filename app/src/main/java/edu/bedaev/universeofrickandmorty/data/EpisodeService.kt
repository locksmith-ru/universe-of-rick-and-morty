package edu.bedaev.universeofrickandmorty.data

import edu.bedaev.universeofrickandmorty.domain.model.Episode
import edu.bedaev.universeofrickandmorty.domain.model.ListItem
import edu.bedaev.universeofrickandmorty.network.api.EpisodeApi
import javax.inject.Inject

class EpisodeService @Inject constructor(
    private val api: EpisodeApi
) : NetworkService {
    override suspend fun fetchData(
        page: Int,
        name: String?,
        status: String?,
        species: String?,
        gender: String?
    ): List<ListItem> {
        return api.getEpisodes(
            page = page,
            filterByName = name
        ).episodes
            .map { dto ->
                Episode(dto = dto)
            }
    }

    override suspend fun fetchSingleData(id: String): List<ListItem> {
        return listOf(Episode(dto = api.getSingleEpisode(id = id)))
    }

    override suspend fun fetchMultipleData(ids: String): List<ListItem> {
        return api.getMultipleEpisodes(ids = ids)
            .map { dto -> Episode(dto = dto) }
    }
}