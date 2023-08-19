package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import edu.bedaev.universeofrickandmorty.database.entity.LocationEnt

@Dao
abstract class LocationsDao : BaseDao<LocationEnt>{
    @Query("SELECT * FROM locations ORDER BY page")
    abstract override fun getEntities(): PagingSource<Int, LocationEnt>

    @Query("DELETE FROM locations")
    abstract override suspend fun deleteAll()

    open suspend fun refresh(newEntities: List<LocationEnt>){
        deleteAll()
        saveAll(list = newEntities)
    }
}