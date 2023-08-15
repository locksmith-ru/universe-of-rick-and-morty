package edu.bedaev.universeofrickandmorty.domain.model

import edu.bedaev.universeofrickandmorty.database.model.PersonEnt
import edu.bedaev.universeofrickandmorty.network.model.Location
import edu.bedaev.universeofrickandmorty.network.model.Origin
import edu.bedaev.universeofrickandmorty.network.model.PersonDto

data class Person(
    override val id: Int,
    override val name: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    val origin: Origin?,
    val location: Location?,
    val image: String?,
    val episodeList: List<String>,
    override val url: String?,
    override val created: String?
) : ListItem {
    constructor(dto: PersonDto) : this(
        id                  = dto.id,
        name                = dto.name,
        status              = dto.status,
        species             = dto.species,
        type                = dto.type,
        gender              = dto.gender,
        origin              = dto.origin,
        location            = dto.location,
        image               = dto.image,
        episodeList         = dto.episodeList,
        url                 = dto.url,
        created             = dto.created
    )

    constructor(entity: PersonEnt) : this(
        id                  = entity.id,
        name                = entity.name,
        status              = entity.status,
        species             = entity.species,
        type                = entity.type,
        gender              = entity.gender,
        origin              = entity.origin,
        location            = entity.location,
        image               = entity.image,
        episodeList         = entity.episodeList,
        url                 = entity.url,
        created             = entity.created
    )

    companion object {
        private var id: Int = 0
        private val statuses: List<String> = listOf("Alive", "Dead", "unknown")
        private val species: List<String> = listOf(
            "Human", "Animal", "Alien",
            "Android", "Fish", "Bird", "unknown"
        )
        private val genders: List<String> = listOf("Male", "Female", "Genderless", "unknown")
        private val origin = Origin.fakeOrigin()
        fun fakePerson(): Person = Person(
            id = id++,
            name = "${generateName((4..10).random())} ${generateName((4..10).random())}",
            status = statuses.random(),
            species = species.random(),
            type = "",
            gender = genders.random(),
            origin = origin,
            location = Location(origin.name, origin.url),
            image = null,
            episodeList = emptyList(),
            url = Origin.generateUrl(pathLength = 5),
            created = generateDateStamp()
        )

        fun generateName(length: Int = 10, startCapitalLetter: Boolean = true): String {
            val allowedChars = ('a'..'z')
            return (1..length)
                .map { idx ->
                    var char = allowedChars.random()
                    if (startCapitalLetter && idx == 1) char = char.uppercaseChar()
                    char
                }.joinToString(separator = "")
        }

        fun generateDateStamp(): String {
            // "2017-11-04T19:09:56.428Z"
            val year = (1978..2023).random()
            val month = (1..12).random()
            val day = (1..30).random()
            val hour = (0..24).random()
            val min = (0..60).random()
            val sec = (0..60).random()
            val utc = ".${(0..9).random()}${(0..9).random()}${(0..9).random()}Z"
            return "$year-$month-${day}T$hour:$min:$sec$utc"
        }
    }
}
