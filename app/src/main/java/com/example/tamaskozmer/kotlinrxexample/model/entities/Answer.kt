package com.example.tamaskozmer.kotlinrxexample.model.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
@Entity
data class Answer(
        @SerializedName("answer_id") @PrimaryKey var answerId: Long,
        @SerializedName("question_id") var questionId: Long,
        @SerializedName("score") var score: Long,
        @SerializedName("is_accepted") var accepted: Boolean,
        @SerializedName("owner") @Embedded var owner: Owner) {

    constructor() : this(-1, -1, 0, false, Owner())
}