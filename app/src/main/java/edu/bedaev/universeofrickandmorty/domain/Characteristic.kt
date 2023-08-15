package edu.bedaev.universeofrickandmorty.domain

enum class Status(val value: String) {
    ALIVE("alive"),
    DEAD("dead"),
    UNKNOWN("unknown")
}

enum class Species(val value: String) {
    HUMAN("human"),
    ANIMAL("animal"),
    ALIEN("alien"),
    UNKNOWN("unknown")
}

enum class Gender(val value: String) {
    MALE("male"),
    FEMALE("female"),
    GENDERLESS("genderless"),
    UNKNOWN("unknown")
}