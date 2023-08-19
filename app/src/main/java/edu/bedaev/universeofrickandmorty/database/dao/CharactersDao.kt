package edu.bedaev.universeofrickandmorty.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import edu.bedaev.universeofrickandmorty.database.entity.PersonEnt

@Dao
abstract class CharactersDao : BaseDao<PersonEnt>{

    @Query("SELECT * FROM characters ORDER BY page")
    abstract override fun getEntities(): PagingSource<Int, PersonEnt>

    @Query("DELETE FROM characters")
    abstract override suspend fun deleteAll()

    @Transaction
    open suspend fun refresh(newEntities: List<PersonEnt>){
        deleteAll()
        saveAll(list = newEntities)
    }

}
