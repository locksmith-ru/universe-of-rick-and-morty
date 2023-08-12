package edu.bedaev.universeofrickandmorty.network.model

data class Info(
    var count: Int? = null,
    var pages: Int? = null,
    var next: String? = null,
    var prev: String? = null
)
