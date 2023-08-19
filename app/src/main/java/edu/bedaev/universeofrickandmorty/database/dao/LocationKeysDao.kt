package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.LocationRemoteKeys

@Dao
abstract class LocationKeysDao : BaseKeyDao<LocationRemoteKeys> {

    @Query("SELECT * FROM location_remote_keys WHERE list_item_id = :listItemId")
    abstract override suspend fun getKeyByListItemId(listItemId: Int): LocationRemoteKeys?

    @Query("SELECT created_at FROM location_remote_keys ORDER BY created_at DESC LIMIT 1")
    abstract override suspend fun getCreationTime(): Long?

    @Query("DELETE FROM location_remote_keys")
    abstract override suspend fun deleteAll()

    @Transaction
    open suspend fun refresh(keys: List<LocationRemoteKeys>) {
        deleteAll()
        saveAll(keys = keys)
    }
}