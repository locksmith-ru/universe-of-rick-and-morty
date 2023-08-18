package edu.bedaev.universeofrickandmorty.network.api

import edu.bedaev.universeofrickandmorty.network.model.EpisodesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface EpisodeApi {

    @GET("/api/episode")
    fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") filterByName: String? = null
    ): EpisodesDto
}