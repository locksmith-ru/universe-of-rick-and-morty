package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeRemoteKeys

@Dao
abstract class EpisodeKeysDao : BaseKeyDao<EpisodeRemoteKeys> {

    @Query("SELECT * FROM episode_remote_keys WHERE list_item_id = :listItemId")
    abstract override suspend fun getKeyByListItemId(listItemId: Int): EpisodeRemoteKeys?

    @Query("SELECT created_at FROM episode_remote_keys ORDER BY created_at DESC LIMIT 1")
    abstract override suspend fun getCreationTime(): Long?

    @Query("DELETE FROM episode_remote_keys")
    abstract override suspend fun deleteAll()

    @Transaction
    open suspend fun refresh(keys: List<EpisodeRemoteKeys>) {
        deleteAll()
        saveAll(keys = keys)
    }

}