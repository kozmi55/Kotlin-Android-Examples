package com.example.tamaskozmer.kotlinrxexample.model.services

import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerListModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionListModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
interface UserService {
    @GET("/users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsers(@Query("page") page: Int = 1) : Single<UserListModel>

    @GET("/users/{userId}/questions?order=desc&sort=votes&site=stackoverflow")
    fun getQuestionsByUser(@Path("userId") userId: Long) : Single<QuestionListModel>

    @GET("/users/{userId}/favorites?order=desc&sort=votes&site=stackoverflow")
    fun getFavoritesByUser(@Path("userId") userId: Long) : Single<QuestionListModel>

    @GET("/users/{userId}/answers?order=desc&sort=votes&site=stackoverflow")
    fun getAnswersByUser(@Path("userId") userId: Long) : Single<AnswerListModel>
}