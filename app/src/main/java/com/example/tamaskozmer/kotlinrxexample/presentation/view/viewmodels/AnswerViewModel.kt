package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
data class AnswerViewModel(
    val answerId: Long,
    val score: Long,
    val accepted: Boolean,
    val questionTitle: String
) : ViewType {

    override fun getViewType() = AdapterConstants.ANSWER
}