package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.CharacterRemoteKeys

@Dao
abstract class CharacterKeysDao : BaseKeyDao<CharacterRemoteKeys> {

    @Query("SELECT * FROM character_remote_keys WHERE list_item_id = :listItemId")
    abstract override suspend fun getKeyByListItemId(listItemId: Int): CharacterRemoteKeys?

    @Query("SELECT created_at FROM character_remote_keys ORDER BY created_at DESC LIMIT 1")
    abstract override suspend fun getCreationTime(): Long?

    @Query("SELECT prev_query FROM character_remote_keys ORDER BY created_at DESC LIMIT 1")
    abstract override suspend fun getPreviousQuery(): String?

    @Query("DELETE FROM character_remote_keys")
    abstract override suspend fun deleteAll()

    @Transaction
    open suspend fun refresh(keys: List<CharacterRemoteKeys>) {
        deleteAll()
        saveAll(keys = keys)
    }
}