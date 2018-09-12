package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata

data class DetailsViewData(
    val questions: List<QuestionViewData>,
    val answers: List<AnswerViewData>,
    val favorites: List<QuestionViewData>
)