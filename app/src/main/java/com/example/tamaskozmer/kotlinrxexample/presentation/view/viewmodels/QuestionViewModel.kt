package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.AdapterConstants
import com.example.tamaskozmer.kotlinrxexample.presentation.view.adapters.ViewType

data class QuestionViewModel(
    val viewCount: Long,
    val score: Long,
    val title: String,
    val link: String,
    val questionId: Long
) : ViewType {

        override fun getViewType(): Int = AdapterConstants.QUESTION
}