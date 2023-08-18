package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<EpisodeEnt>)

    @Query("SELECT * FROM episodes ORDER BY page")
    fun getEntities(): PagingSource<Int, EpisodeEnt>

    @Query("DELETE FROM episodes")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntityList: List<EpisodeEnt>) {
        deleteAll()
        saveAll(entityList = newEntityList)
    }

}

/*
@Dao
interface EpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<EpisodeEnt>)

    @Query("SELECT * FROM episodes ORDER BY page")
    fun getEntities(): PagingSource<Int, EpisodeEnt>

    @Query("DELETE FROM episodes")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntityList: List<EpisodeEnt>) {
        deleteAll()
        saveAll(entityList = newEntityList)
    }
}
 */