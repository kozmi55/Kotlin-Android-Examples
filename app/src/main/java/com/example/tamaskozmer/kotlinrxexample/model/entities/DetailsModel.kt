package com.example.tamaskozmer.kotlinrxexample.model.entities

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
data class DetailsModel(
        val questions: List<Question>,
        val answers: List<AnswerViewModel>,
        val favorites: List<Question>)