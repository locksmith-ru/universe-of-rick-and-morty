package edu.bedaev.universeofrickandmorty.network.model

import com.google.gson.annotations.SerializedName

data class PersonDto(
    //The id of the character.
    var id: Int? = null,
    // The name of the character.
    var name: String? = null,
    // The status of the character ('Alive', 'Dead' or 'unknown').
    var status: String? = null,
    // The species of the character.
    var species: String? = null,
    // The type or subspecies of the character.
    var type: String? = null,
    // The gender of the character ('Female', 'Male', 'Genderless' or 'unknown').
    var gender: String? = null,
    // Name and link to the character's origin location.
    var origin: Origin? = Origin(),
    // Name and link to the character's last known location endpoint.
    var location: Location? = Location(),
    // Link to the character's image. All images are 300x300px and most are medium shots or
    // portraits since they are intended to be used as avatars.
    var image: String? = null,
    // List of episodes in which this character appeared.
    @SerializedName("episode") var episodeList: ArrayList<String> = arrayListOf(),
    // Link to the character's own URL endpoint.
    var url: String? = null,
    // Time at which the character was created in the database.
    var created: String? = null
)
