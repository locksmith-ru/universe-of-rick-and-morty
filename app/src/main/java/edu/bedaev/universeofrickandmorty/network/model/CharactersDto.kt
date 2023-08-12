package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class CharactersDto(
    var info: Info? = Info(),
    @SerializedName("results") var persons: ArrayList<PersonDto> = arrayListOf()
)
