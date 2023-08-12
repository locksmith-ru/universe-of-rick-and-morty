package edu.bedaev.universeofrickandmorty.domain.model

import edu.bedaev.universeofrickandmorty.network.model.Location
import edu.bedaev.universeofrickandmorty.network.model.Origin
import edu.bedaev.universeofrickandmorty.network.model.PersonDto

data class Person(
    override val id: Int,
    val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val image: String?,
    val episodeList: List<String>,
    var url: String?,
    var created: String?
) : ListItem {
    constructor(dto: PersonDto) : this(
        id = dto.id!!,
        name = dto.name,
        status = dto.status,
        species = dto.species,
        type = dto.type,
        gender = dto.gender,
        origin = dto.origin,
        location = dto.location,
        image = dto.image,
        episodeList = dto.episodeList,
        url = dto.url,
        created = dto.created
    )
}
