package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import edu.bedaev.universeofrickandmorty.database.entity.RemoteKey

interface BaseKeyDao<T: RemoteKey> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    @JvmSuppressWildcards
    suspend fun saveAll(keys: List<T>)

    suspend fun getKeyByListItemId(listItemId: Int): T?

    suspend fun getCreationTime(): Long?

    suspend fun getPreviousQuery(): String?

    suspend fun deleteAll()
}