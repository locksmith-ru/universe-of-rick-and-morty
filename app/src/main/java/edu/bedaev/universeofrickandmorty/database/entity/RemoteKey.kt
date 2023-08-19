package edu.bedaev.universeofrickandmorty.database.entity

interface RemoteKey {
    val listItemId: Int
    val prevKey: Int?
    val currentPage: Int
    val nextKey: Int?
    val createdAt: Long
}