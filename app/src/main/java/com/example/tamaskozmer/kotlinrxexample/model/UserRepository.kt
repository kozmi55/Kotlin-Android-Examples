package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.*
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import io.reactivex.Single
import io.reactivex.functions.Function3

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService,
        private val questionService: QuestionService) {

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getDetails(userId: Long) : Single<DetailsModel> {
        return Single.zip(
                userService.getQuestionsByUser(userId),
                getAnswers(userId),
                userService.getFavoritesByUser(userId),
                Function3<QuestionListModel, List<AnswerViewModel>, QuestionListModel, DetailsModel>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun getAnswers(userId: Long) : Single<List<AnswerViewModel>> {
        return userService.getAnswersByUser(userId)
                .flatMap { answerListModel: AnswerListModel ->
                    mapAnswersToAnswerViewModels(answerListModel.items) }
    }

    private fun mapAnswersToAnswerViewModels(answers: List<Answer>): Single<List<AnswerViewModel>> {
        val ids = answers
                .map { it.questionId.toString() }
                .joinToString(separator = ";")

        val questionsListModel = questionService.getQuestionById(ids)

        return questionsListModel
                .map { questionListModel: QuestionListModel? ->
                    addTitlesToAnswers(answers, questionListModel?.items ?: emptyList()) }
    }

    private fun addTitlesToAnswers(answers: List<Answer>, questions: List<Question>) : List<AnswerViewModel> {
        return answers.map { (answerId, questionId, score, accepted) ->
            val question = questions.find { it.questionId == questionId }
            AnswerViewModel(answerId, score, accepted, question?.title ?: "Unknown")
        }
    }

    private fun createDetailsModel(questionsModel: QuestionListModel, answersModel: List<AnswerViewModel>,
                                   favoritesModel: QuestionListModel): DetailsModel {
        val questions = questionsModel.items
                .take(3)

        val favorites = favoritesModel.items
                .take(3)

        val answers = answersModel
                .filter { it.accepted }
                .take(3)

        return DetailsModel(questions, answers, favorites)
    }
}