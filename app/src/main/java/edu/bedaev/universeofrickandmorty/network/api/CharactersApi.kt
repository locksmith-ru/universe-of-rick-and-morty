package edu.bedaev.universeofrickandmorty.network.api

import edu.bedaev.universeofrickandmorty.network.model.CharactersDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CharactersApi {

    @GET("/api/character")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") filterByName: String? = null,
        @Query("status") filterByStatus: String? = null,
        @Query("species") filterBySpecies: String? = null,
        @Query("gender") filterByGender: String? = null
    ): CharactersDto
}