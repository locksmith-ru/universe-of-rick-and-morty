package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class Episodes(
    var info: Info? = Info(),
    @SerializedName("results") var episodes: ArrayList<EpisodeDto> = arrayListOf()
)
