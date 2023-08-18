package edu.bedaev.universeofrickandmorty.domain.model

import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt
import edu.bedaev.universeofrickandmorty.network.model.EpisodeDto
import edu.bedaev.universeofrickandmorty.network.model.Origin

data class Episode(
    override val id: Int,
    override val name: String,
    val airDate: String? = null,
    val episode: String? = null,
    val characters: List<String> = emptyList(),
    override val url: String? = null,
    override val created: String? = null
) : ListItem {
    constructor(dto: EpisodeDto) : this(
        id = dto.id,
        name = dto.name,
        airDate = dto.airDate,
        episode = dto.episode,
        characters = dto.characters,
        url = dto.url,
        created = dto.created
    )

    constructor(entity: EpisodeEnt): this(
        id = entity.id,
        name = entity.name,
        airDate = entity.airDate,
        episode = entity.episode,
        characters = entity.characters,
        url = entity.url,
        created = entity.created
    )

    companion object {
        private var id: Int = 0
        fun fakeEpisode(): Episode {
            return Episode(
                id = id++,
                name = Person.generateName((5..15).random()),
                airDate = generateDate(),
                episode = generateCode(),
                characters = emptyList(),
                url = Origin.generateUrl(5),
                created = Person.generateDateStamp()
            )
        }

        private fun generateDate(): String {
            val month = listOf(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
            ).random()
            val day = (1..30).random()
            val year = (1978..2023).random()
            return "$month $day, $year"
        }

        private fun generateCode(): String {
            return "${generateString(enableUppercase = true)}${generateNumericSequence(2)}" +
                    "${generateString(enableUppercase = true)}${generateNumericSequence(2)}"
        }

        private fun generateString(len: Int = 1, enableUppercase: Boolean = false): String {
            val chars = ('A'..'Z') + ('a'..'z')
            return (1..len).map {
                var char = chars.random()
                if (enableUppercase) char = char.uppercaseChar()
                char
            }.joinToString(separator = "")
        }

        private fun generateNumericSequence(len: Int): String {
            val numbers = (0..9)
            return (1..len)
                .map { numbers.random() }
                .joinToString(separator = "")
        }
    }
}
