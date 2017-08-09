package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import io.reactivex.Single

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
interface DetailsRepository {
    fun getQuestionsByUser(userId: Long, forced: Boolean): Single<List<Question>>
    fun getAnswersByUser(userId: Long, forced: Boolean): Single<List<Answer>>
    fun getFavoritesByUser(userId: Long, forced: Boolean): Single<List<Question>>
    fun getQuestionsById(ids: List<Long>, userId: Long, forced: Boolean): Single<List<Question>>
}