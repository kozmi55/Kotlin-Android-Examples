package com.example.tamaskozmer.kotlinrxexample.model.persistence.typeconverters

import android.arch.persistence.room.TypeConverter

/**
 * Created by Tamas_Kozmer on 7/18/2017.
 */
class FavoritedByUserConverter {

    @TypeConverter
    fun fromString(string: String) : List<Long> {
        if (string == "") {
            return emptyList()
        }
        return string.split(";").map { it.toLong() }
    }

    @TypeConverter
    fun toString(ids: List<Long>) : String {
        return ids.joinToString(separator = ";")
    }
}