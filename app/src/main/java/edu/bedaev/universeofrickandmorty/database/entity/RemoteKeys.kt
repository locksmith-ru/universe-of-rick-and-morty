package edu.bedaev.universeofrickandmorty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "list_item_id") val listItemId: Int,
    @ColumnInfo(name = "prev_key") val prevKey: Int?,
    @ColumnInfo(name = "current_page") val currentPage: Int,
    @ColumnInfo(name = "next_key") val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)