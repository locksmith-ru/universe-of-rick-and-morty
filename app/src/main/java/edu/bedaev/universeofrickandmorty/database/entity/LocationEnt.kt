package edu.bedaev.universeofrickandmorty.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.bedaev.universeofrickandmorty.domain.model.Location

@Entity(tableName = "locations")
data class LocationEnt(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val name: String,
    override val page: Int,
    val type: String? = null,
    val dimension: String? = null,
    val residents: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null

):DbEntity {
    constructor(location: Location, page: Int): this(
        id = location.id,
        name = location.name,
        type = location.type,
        dimension = location.dimension,
        residents = location.residents,
        url = location.url,
        created = location.created,
        page = page
    )
}