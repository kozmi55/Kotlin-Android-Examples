package com.example.tamaskozmer.kotlinrxexample.model.entities

import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
data class User(
        @SerializedName("user_id") val userId: Long,
        @SerializedName("display_name") val displayName: String,
        @SerializedName("reputation") val reputation: Long,
        @SerializedName("profile_image") val profileImage: String)