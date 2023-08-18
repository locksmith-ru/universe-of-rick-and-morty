package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class EpisodesDto(
    var info: Info? = Info(),
    @SerializedName("results") var episodes: ArrayList<EpisodeDto> = arrayListOf()
)
