package edu.bedaev.universeofrickandmorty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.bedaev.universeofrickandmorty.domain.model.Episode

@Entity(tableName = "episodes")
data class EpisodeEnt(
    @PrimaryKey(autoGenerate = false)
    override val id: Int,
    override val name: String,
    override val page: Int,
    @ColumnInfo("air_data") val airDate: String? = null,
    val episode: String? = null,
    val characters: List<String> = emptyList(),
    val url: String? = null,
    val created: String? = null

): DbEntity{
    constructor(model: Episode, page: Int): this(
        id = model.id,
        name = model.name,
        airDate = model.airDate,
        episode = model.episode,
        characters = model.characters,
        url = model.url,
        created = model.created,
        page = page

    )
}