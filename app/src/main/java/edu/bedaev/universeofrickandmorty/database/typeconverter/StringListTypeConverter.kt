package edu.bedaev.universeofrickandmorty.database.typeconverter

import androidx.room.TypeConverter

class StringListTypeConverter {
    @TypeConverter
    fun fromListToString(list: List<String>?): String? {
        return list?.joinToString(separator = ",")
    }

    @TypeConverter
    fun fromStringToList(value: String?): List<String>? {
        return value?.split(",")
    }
}