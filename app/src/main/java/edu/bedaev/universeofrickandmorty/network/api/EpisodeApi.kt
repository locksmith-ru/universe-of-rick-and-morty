package edu.bedaev.universeofrickandmorty.network.api

import edu.bedaev.universeofrickandmorty.network.model.EpisodeDto
import edu.bedaev.universeofrickandmorty.network.model.EpisodesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodeApi {

    @GET("/api/episode")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") filterByName: String? = null
    ): EpisodesDto

    @GET("/api/episode/{multiIds}")
    suspend fun getMultipleEpisodes(
        @Path("multiIds") ids: String
    ): List<EpisodeDto>

    @GET("/api/episode/{id}")
    suspend fun getSingleEpisode(
        @Path("id") id: String
    ): EpisodeDto
}