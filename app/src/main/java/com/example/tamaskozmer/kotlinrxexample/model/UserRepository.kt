package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerListModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerViewModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionListModel
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import io.reactivex.Single
import io.reactivex.functions.Function3

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService) {

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getDetails(userId: Long) : Single<DetailsModel> {
        return Single.zip(
                userService.getQuestionsByUser(userId),
                userService.getAnswersByUser(userId),
                userService.getFavoritesByUser(userId),
                Function3<QuestionListModel, AnswerListModel, QuestionListModel, DetailsModel>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun createDetailsModel(questionsModel: QuestionListModel, answersModel: AnswerListModel,
                                   favoritesModel: QuestionListModel): DetailsModel {
        val questions = questionsModel.items
                .take(3)

        val favorites = favoritesModel.items
                .take(3)

        val answers = answersModel.items
                .filter { it.accepted }
                .take(3)
                .map { AnswerViewModel(it.answerId, it.score, it.accepted, "TODO") }

        return DetailsModel(questions, answers, favorites)
    }
}