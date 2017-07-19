package com.example.tamaskozmer.kotlinrxexample.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/19/2017.
 */
data class Owner(@SerializedName("user_id") var userId: Long) {

    constructor() : this(-1)
}