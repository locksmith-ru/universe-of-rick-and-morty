package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import edu.bedaev.universeofrickandmorty.database.entity.EpisodeEnt

@Dao
abstract class EpisodesDao : BaseDao<EpisodeEnt>{
    @Query("SELECT * FROM episodes ORDER BY page")
    abstract fun getEntities(): PagingSource<Int, EpisodeEnt>

    @Query("DELETE FROM episodes")
    abstract fun deleteAll()

}

/*@Dao
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

}*/

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