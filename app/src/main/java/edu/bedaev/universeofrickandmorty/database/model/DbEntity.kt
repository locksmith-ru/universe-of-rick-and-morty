package edu.bedaev.universeofrickandmorty.database.model

interface DbEntity {
    val id: Int
    val name: String
    val page: Int
}