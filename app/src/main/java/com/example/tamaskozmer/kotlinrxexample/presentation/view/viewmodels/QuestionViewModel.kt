package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import com.example.tamaskozmer.kotlinrxexample.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.view.adapters.viewtypes.ViewType

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
data class QuestionViewModel(
    val viewCount: Long,
    val score: Long,
    val title: String,
    val link: String,
    val questionId: Long) : ViewType {

        override fun getViewType(): Int = AdapterConstants.QUESTION
}