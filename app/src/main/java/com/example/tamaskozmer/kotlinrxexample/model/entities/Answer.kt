package com.example.tamaskozmer.kotlinrxexample.model.entities

import com.example.tamaskozmer.kotlinrxexample.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType
import com.google.gson.annotations.SerializedName

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
data class Answer(
    @SerializedName("answer_id") val answerId: Long,
    @SerializedName("question_id") val questionId: Long,
    @SerializedName("score") val score: Long,
    @SerializedName("is_accepted") val accepted: Boolean) : ViewType {

    override fun getViewType(): Int = AdapterConstants.ANSWER
}