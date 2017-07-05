package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerListModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.DetailsModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionListModel
import retrofit.Retrofit
import rx.Single

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(private val retrofit: Retrofit) {

    private val userService by lazy { retrofit.create(UserService::class.java) }

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getDetails(userId: Long) : Single<DetailsModel> {
        return Single.zip(
                userService.getQuestionsByUser(userId),
                userService.getAnswersByUser(userId),
                userService.getFavoritesByUser(userId),
                { questions, answers, favorites
                    -> createDetailsModel(questions, answers, favorites) })
    }

    private fun createDetailsModel(questionsModel: QuestionListModel?, answersModel: AnswerListModel?,
                                   favoritesModel: QuestionListModel?): DetailsModel {
        val questions = (questionsModel?.items ?: emptyList())
                .take(3)

        val answers = (answersModel?.items ?: emptyList())
                .filter { it.accepted }
                .take(3)

        val favorites = (favoritesModel?.items ?: emptyList())
                .take(3)

        return DetailsModel(questions, answers, favorites)
    }
}