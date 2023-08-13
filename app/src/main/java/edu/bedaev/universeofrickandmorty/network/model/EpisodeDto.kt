package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class EpisodeDto(
    // The id of the episode.
    var id: Int,
    // The name of the episode.
    var name: String? = null,
    // The air date of the episode.
    @SerializedName("air_date") var airDate: String? = null,
    // The code of the episode.
    var episode: String? = null,
    // List of characters who have been seen in the episode.
    var characters: ArrayList<String> = arrayListOf(),
    // Link to the episode's own endpoint.
    var url: String? = null,
    // Time at which the episode was created in the database.
    var created: String? = null
)
