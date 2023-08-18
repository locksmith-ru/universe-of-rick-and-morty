package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt

@Dao
abstract class CharactersDao : BaseDao<PersonEnt>{

    @Query("SELECT * FROM characters ORDER BY page")
    abstract fun getEntities(): PagingSource<Int, PersonEnt>

    @Query("DELETE FROM characters")
    abstract fun deleteAll()

}

/*
@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAll(entityList: List<PersonEnt>)

    @Query("SELECT * FROM characters ORDER BY page")
    fun getEntities(): PagingSource<Int, PersonEnt>

    @Query("DELETE FROM characters")
    suspend fun deleteAll()

    @Transaction
    suspend fun refresh(newEntities: List<PersonEnt>){
        deleteAll()
        saveAll(entityList = newEntities)
    }

}*/
