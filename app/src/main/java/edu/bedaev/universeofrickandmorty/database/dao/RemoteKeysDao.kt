package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.RemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(keys: List<RemoteKeys>)

    @Query("SELECT * FROM remote_keys WHERE list_item_id = :listItemId")
    suspend fun getKeyByListItemId(listItemId: Int): RemoteKeys?

    @Query("SELECT created_at FROM remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM remote_keys")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(keys: List<RemoteKeys>) {
        deleteAll()
        saveAll(keys = keys)
    }
}