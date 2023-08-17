package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.model.PersonEnt

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<PersonEnt>)

    @Query("SELECT * FROM characters ORDER BY page")
    fun getEntities(): PagingSource<Int, PersonEnt>

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntityList: List<PersonEnt>) {
        deleteAll()
        saveAll(entityList = newEntityList)
    }
}