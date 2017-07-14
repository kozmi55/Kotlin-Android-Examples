package com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
data class DetailsViewModel(
        val questions: List<QuestionViewModel>,
        val answers: List<AnswerViewModel>,
        val favorites: List<QuestionViewModel>)