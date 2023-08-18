package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(keys: List<CharacterRemoteKeys>)

    @Query("SELECT * FROM character_remote_keys WHERE list_item_id = :listItemId")
    suspend fun getKeyByListItemId(listItemId: Int): CharacterRemoteKeys?

    @Query("SELECT created_at FROM character_remote_keys ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?

    @Query("DELETE FROM character_remote_keys")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(keys: List<CharacterRemoteKeys>) {
        deleteAll()
        saveAll(keys = keys)
    }
}