package edu.bedaev.universeofrickandmorty.network.api

import edu.bedaev.universeofrickandmorty.network.model.LocationDto
import edu.bedaev.universeofrickandmorty.network.model.LocationsDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationApi {

    @GET("/api/location")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") filterByName: String? = null
    ): LocationsDto

    @GET("/api/location/{ids}")
    suspend fun getMultipleLocations(
        @Path("ids") ids: String
    ): List<LocationDto>

    @GET("/api/location/{id}")
    suspend fun getSingleLocation(
        @Path("id") id: String
    ): LocationDto

}