package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService,
        private val questionService: QuestionService) {

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getQuestionsByUser(userId: Long) = userService.getQuestionsByUser(userId)

    fun getAnswersByUser(userId: Long) = userService.getAnswersByUser(userId)

    fun getFavoritesByUser(userId: Long) = userService.getFavoritesByUser(userId)

    fun getQuestionsById(ids: String) = questionService.getQuestionById(ids)
}