package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata

import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

data class QuestionViewData(
    val viewCount: Long,
    val score: Long,
    val title: String,
    val link: String,
    val questionId: Long
) : ViewType {

        override fun getViewType(): Int = AdapterConstants.QUESTION
}