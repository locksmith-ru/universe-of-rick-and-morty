package edu.bedaev.universeofrickandmorty.database.dao

interface RemoteKey {
    val listItemId: Int
    val prevKey: Int?
    val currentPage: Int
    val nextKey: Int?
    val createdAt: Long
}