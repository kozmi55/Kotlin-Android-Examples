package com.example.tamaskozmer.kotlinrxexample.util

import android.content.Context
import android.net.ConnectivityManager

class ConnectionHelper(private val context: Context) {

    fun isOnline(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = connectivityManager.activeNetworkInfo
        return netInfo != null && netInfo.isConnected
    }
}