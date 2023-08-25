package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import edu.bedaev.universeofrickandmorty.database.entity.DbEntity

interface BaseDao<T: DbEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun saveAll(list: List<T>)

    fun getEntities(): PagingSource<Int, T>

    suspend fun deleteAll()
}