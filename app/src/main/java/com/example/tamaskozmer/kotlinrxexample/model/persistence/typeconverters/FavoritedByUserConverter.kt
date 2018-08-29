package com.example.tamaskozmer.kotlinrxexample.model.persistence.typeconverters

import android.arch.persistence.room.TypeConverter

class FavoritedByUserConverter {

    @TypeConverter
    fun fromString(string: String): List<Long> {
        if (string == "") {
            return emptyList()
        }
        return string.split(";").map { it.toLong() }
    }

    @TypeConverter
    fun toString(ids: List<Long>): String {
        return ids.joinToString(separator = ";")
    }
}