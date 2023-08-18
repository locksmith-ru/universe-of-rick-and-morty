package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName


data class LocationsDto(
    @SerializedName("info") var info: Info? = Info(),
    @SerializedName("results") var locations: ArrayList<LocationDto> = arrayListOf()
)
