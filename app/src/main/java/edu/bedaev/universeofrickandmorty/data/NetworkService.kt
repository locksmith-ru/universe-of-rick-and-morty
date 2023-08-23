package edu.bedaev.universeofrickandmorty.data

import edu.bedaev.universeofrickandmorty.domain.model.ListItem


interface NetworkService {
    suspend fun fetchData(
        page: Int,
        name: String? = null,
        status: String? = null,
        species: String? = null,
        gender: String? = null
    ): List<ListItem>

    suspend fun fetchMultipleData(ids: String): List<ListItem>

    suspend fun fetchSingleData(id: String): List<ListItem>

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
    }
}