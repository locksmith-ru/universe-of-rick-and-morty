package edu.bedaev.universeofrickandmorty.domain.model

import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt
import edu.bedaev.universeofrickandmorty.network.model.LocationDto
import edu.bedaev.universeofrickandmorty.network.model.Origin

data class Location(
    override val id: Int,
    override val name: String,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String> = emptyList(),
    override val url: String? = null,
    override val created: String? = null
): ListItem{
    constructor(dto: LocationDto): this(
        id = dto.id,
        name = dto.name,
        type = dto.type,
        dimension = dto.dimension,
        residents = dto.residents,
        url = dto.url,
        created = dto.created
    )

    constructor(entity: LocationEnt): this(
        id = entity.id,
        name = entity.name,
        type = entity.type,
        dimension = entity.dimension,
        residents = entity.residents,
        url = entity.url,
        created = entity.created
    )

    companion object{
        private var id = 0
        fun fakeLocation(): Location =
            Location(
                id = id++,
                name = Person.generateName((5..10).random()),
                type = Person.generateName((5..10).random()),
                dimension = "Dimension ${Person.generateName((3..6).random())}",
                residents = emptyList(),
                url = Origin.generateUrl(),
                created = Person.generateDateStamp()
            )
    }
}
