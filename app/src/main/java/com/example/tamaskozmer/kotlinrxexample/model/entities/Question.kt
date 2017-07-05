package com.example.tamaskozmer.kotlinrxexample.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
data class Question(
        @SerializedName("view_count") val viewCount: Long,
        @SerializedName("score") val score: Long,
        @SerializedName("title") val title: String,
        @SerializedName("link") val link: String,
        @SerializedName("creation_date") val creationDate: Long)