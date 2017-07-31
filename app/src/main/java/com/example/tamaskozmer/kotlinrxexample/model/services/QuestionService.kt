package com.example.tamaskozmer.kotlinrxexample.model.services

import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Tamas_Kozmer on 7/5/2017.
 */
interface QuestionService {
    @GET("/questions/{questionIds}?order=desc&sort=activity&site=stackoverflow")
    fun getQuestionsById(@Path("questionIds") questionId: String) : Call<QuestionList>
}