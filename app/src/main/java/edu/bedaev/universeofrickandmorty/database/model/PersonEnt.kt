package edu.bedaev.universeofrickandmorty.database.model

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
    val id: Int,
    val name: String,
    val status: String?,
    val species: String?,
    val type: String?,
    val gender: String?,
    @Embedded(prefix = "origin_") val origin: Origin?,
    @Embedded(prefix = "location_") val location: Location?,
    val image: String?,
    @ColumnInfo("episode_list") val episodeList: List<String>,
    val url: String?,
    val created: String?,
    val page: Int
) {
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