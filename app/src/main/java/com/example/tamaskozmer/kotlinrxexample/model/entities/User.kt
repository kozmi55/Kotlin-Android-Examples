package com.example.tamaskozmer.kotlinrxexample.model.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
@Entity
data class User(
        @SerializedName("user_id") @PrimaryKey var userId: Long,
        @SerializedName("display_name") var displayName: String,
        @SerializedName("reputation") var reputation: Long,
        @SerializedName("profile_image") var profileImage: String) {

    constructor() : this(-1, "", 0, "")
}