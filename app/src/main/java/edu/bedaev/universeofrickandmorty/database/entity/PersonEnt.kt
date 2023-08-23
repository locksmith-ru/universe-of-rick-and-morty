package edu.bedaev.universeofrickandmorty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.bedaev.universeofrickandmorty.domain.model.Person
import edu.bedaev.universeofrickandmorty.network.model.Location
import edu.bedaev.universeofrickandmorty.network.model.Origin

@Entity(tableName = "characters")
data class PersonEnt(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val name: String?,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    @Embedded(prefix = "origin_") val origin: Origin?,
    @Embedded(prefix = "location_") val location: Location?,
    val image: String?,
    @ColumnInfo("episode_list") val episodeList: List<String>?,
    val url: String?,
    val created: String?,
    override val page: Int
) : DbEntity {
    constructor(person: Person, page: Int = 1) : this(
        id = person.id,
        name = person.name,
        status = person.status,
        species = person.species,
        type = person.type,
        gender = person.gender,
        origin = person.origin,
        location = person.location,
        image = person.image,
        episodeList = person.episodeList,
        url = person.url,
        created = person.created,
        page = page
    )
}