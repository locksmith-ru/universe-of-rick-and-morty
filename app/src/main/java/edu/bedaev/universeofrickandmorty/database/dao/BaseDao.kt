package edu.bedaev.universeofrickandmorty.database.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import edu.bedaev.universeofrickandmorty.database.entity.DbEntity

interface BaseDao<T: DbEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(obj: T)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun saveAll(list: List<T>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T)

    @Delete
    suspend fun delete(obj: T)

}