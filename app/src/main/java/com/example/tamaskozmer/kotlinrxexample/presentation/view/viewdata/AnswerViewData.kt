package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata

import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

data class AnswerViewData(
    val answerId: Long,
    val score: Long,
    val accepted: Boolean,
    val questionTitle: String
) : ViewType {

    override fun getViewType() = AdapterConstants.ANSWER
}