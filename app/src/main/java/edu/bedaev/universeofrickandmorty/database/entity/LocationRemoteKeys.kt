package edu.bedaev.universeofrickandmorty.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_remote_keys")
data class LocationRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "list_item_id") override val listItemId: Int,
    @ColumnInfo(name = "prev_key") override val prevKey: Int?,
    @ColumnInfo(name = "current_page") override val currentPage: Int,
    @ColumnInfo(name = "next_key") override val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    override val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "prev_query") override val prevQuery: String?

): RemoteKey
