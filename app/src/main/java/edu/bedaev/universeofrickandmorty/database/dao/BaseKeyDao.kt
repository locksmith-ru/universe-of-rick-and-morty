package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy

interface BaseKeyDao<T: RemoteKey> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    @JvmSuppressWildcards
    suspend fun saveAll(keys: List<T>)

}