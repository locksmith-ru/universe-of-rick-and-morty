package edu.bedaev.universeofrickandmorty.network.api

import edu.bedaev.universeofrickandmorty.network.model.LocationsDto
import retrofit2.http.GET
import retrofit2.http.Query

interface LocationApi {

    @GET("/api/location")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") filterByName: String? = null
    ): LocationsDto
}