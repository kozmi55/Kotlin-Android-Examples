package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

data class DetailsViewModel(
    val questions: List<QuestionViewModel>,
    val answers: List<AnswerViewModel>,
    val favorites: List<QuestionViewModel>
)