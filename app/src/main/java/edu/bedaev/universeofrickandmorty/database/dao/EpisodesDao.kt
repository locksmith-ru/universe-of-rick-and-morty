package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt

@Dao
abstract class EpisodesDao : BaseDao<EpisodeEnt>{
    @Query("SELECT * FROM episodes ORDER BY page")
    abstract override fun getEntities(): PagingSource<Int, EpisodeEnt>

    @Query("DELETE FROM episodes")
    abstract override suspend fun deleteAll()

    @Transaction
    open suspend fun refresh(newEntities: List<EpisodeEnt>){
        deleteAll()
        saveAll(list = newEntities)
    }
}