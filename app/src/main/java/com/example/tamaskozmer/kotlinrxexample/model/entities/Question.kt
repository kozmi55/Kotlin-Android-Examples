package com.example.tamaskozmer.kotlinrxexample.model.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Entity
data class Question(
        @SerializedName("view_count") var viewCount: Long,
        @SerializedName("score") var score: Long,
        @SerializedName("title") var title: String,
        @SerializedName("link") var link: String,
        @SerializedName("question_id") @PrimaryKey var questionId: Long,
        @SerializedName("owner") @Embedded var owner: Owner) {

    constructor() : this(0, 0, "", "", -1, Owner())
}
