package edu.bedaev.universeofrickandmorty.network.model


data class LocationDto(
    // The id of the location.
    var id: Int,
    // The name of the location.
    var name: String? = null,
    // The type of the location.
    var type: String? = null,
    // The dimension in which the location is located.
    var dimension: String? = null,
    // List of character who have been last seen in the location.
    var residents: ArrayList<String> = arrayListOf(),
    // Link to the location's own endpoint.
    var url: String? = null,
    // Time at which the location was created in the database.
    var created: String? = null
)