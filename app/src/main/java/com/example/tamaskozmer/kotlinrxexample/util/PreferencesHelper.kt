package com.example.tamaskozmer.kotlinrxexample.util

import android.content.Context
import android.preference.PreferenceManager

/**
 * Created by Tamas_Kozmer on 7/26/2017.
 */
class PreferencesHelper(private val context: Context) {

    fun save(key: String, value: Long) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = preferences.edit()
        editor.putLong(key, value)
        editor.commit()
    }

    fun loadLong(key: String) : Long {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getLong(key, 0L)
    }
}