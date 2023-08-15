package edu.bedaev.universeofrickandmorty.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import edu.bedaev.universeofrickandmorty.database.dao.CharactersDao
import edu.bedaev.universeofrickandmorty.database.dao.EpisodesDao
import edu.bedaev.universeofrickandmorty.database.dao.LocationsDao
import edu.bedaev.universeofrickandmorty.database.dao.RemoteKeysDao
import edu.bedaev.universeofrickandmorty.database.model.PersonEnt
import edu.bedaev.universeofrickandmorty.database.model.RemoteKeys
import edu.bedaev.universeofrickandmorty.database.typeconverter.StringListTypeConverter

@Database(
    entities = [
        PersonEnt::class,
        RemoteKeys::class
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

    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        const val DATABASE_NAME = "rick_and_morty"
    }
}