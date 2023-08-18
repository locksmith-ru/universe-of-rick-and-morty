package edu.bedaev.universeofrickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.bedaev.universeofrickandmorty.database.dao.CharactersDao
import edu.bedaev.universeofrickandmorty.database.dao.EpisodesDao
import edu.bedaev.universeofrickandmorty.database.dao.LocationsDao
import edu.bedaev.universeofrickandmorty.database.dao.CharacterKeysDao
import edu.bedaev.universeofrickandmorty.database.dao.EpisodeKeysDao
import edu.bedaev.universeofrickandmorty.database.dao.LocationKeysDao
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt
import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeRemoteKeys
import edu.bedaev.universeofrickandmorty.database.entity.LocationRemoteKeys
import edu.bedaev.universeofrickandmorty.database.typeconverter.StringListTypeConverter

@Database(
    entities = [
        PersonEnt::class,
        LocationEnt::class,
        EpisodeEnt::class,
        CharacterRemoteKeys::class,
        LocationRemoteKeys::class,
        EpisodeRemoteKeys::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [StringListTypeConverter::class]
)
abstract class RickAndMortyDatabase : RoomDatabase() {

    abstract fun charactersDao(): CharactersDao

    abstract fun locationsDao(): LocationsDao

    abstract fun episodesDao(): EpisodesDao

    abstract fun characterKeysDao(): CharacterKeysDao

    abstract fun locationKeysDao(): LocationKeysDao

    abstract fun episodeKeysDao(): EpisodeKeysDao

    companion object {
        const val DATABASE_NAME = "rick_and_morty"
    }
}