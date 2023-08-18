package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<LocationEnt>)

    @Query("SELECT * FROM locations ORDER BY page")
    fun getEntities(): PagingSource<Int, LocationEnt>

    @Query("DELETE FROM locations")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntities: List<LocationEnt>){
        deleteAll()
        saveAll(entityList = newEntities)
    }

}

/*
@Dao
interface LocationsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<LocationEnt>)

    @Query("SELECT * FROM locations ORDER BY page")
    fun getEntities(): PagingSource<Int, LocationEnt>

    @Query("DELETE FROM locations")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntityList: List<LocationEnt>) {
        deleteAll()
        saveAll(entityList = newEntityList)
    }

}
 */