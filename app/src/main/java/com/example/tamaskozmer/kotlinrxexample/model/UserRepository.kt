package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.*
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import io.reactivex.Single
import io.reactivex.functions.Function3
import retrofit2.Retrofit

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(private val retrofit: Retrofit) {

    private val userService by lazy { retrofit.create(UserService::class.java) } // TODO Eliminate hard dependency
    private val questionService by lazy { retrofit.create(QuestionService::class.java) } // TODO Eliminate hard dependency

    fun getUsers(page: Int) = userService.getUsers(page)

    fun getDetails(userId: Long) : Single<DetailsModel> {
        return Single.zip(
                userService.getQuestionsByUser(userId),
                getTitlesForAnswers(userId),
                userService.getFavoritesByUser(userId),
                Function3<QuestionListModel, List<AnswerViewModel>, QuestionListModel, DetailsModel>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun getTitlesForAnswers(userId: Long) : Single<List<AnswerViewModel>> {
        return userService.getAnswersByUser(userId)
                .flatMap { answerListModel: AnswerListModel? ->
                    mapAnswersToAnswersWithTitle(answerListModel?.items ?: emptyList()) }
    }

    private fun mapAnswersToAnswersWithTitle(answers: List<Answer>): Single<List<AnswerViewModel>> {
        val ids = answers
                .map { it.questionId.toString() }
                .joinToString(separator = ";")

        val questionsListModel = questionService.getQuestionById(ids)

        return questionsListModel
                .flatMap { questionListModel: QuestionListModel? -> Single.just(questionListModel?.items) }
                .map { questions: List<Question>? -> mergeAnswersAndQuestions(answers, questions?: emptyList()) }
    }

    private fun mergeAnswersAndQuestions(answers: List<Answer>, questions: List<Question>) : List<AnswerViewModel> {
        return answers.map { (answerId, questionId, score, accepted) ->
            val question = questions.find { it.questionId == questionId }
            AnswerViewModel(answerId, score, accepted, question?.title ?: "Unknown")
        }
    }

    private fun createDetailsModel(questionsModel: QuestionListModel?, answers: List<AnswerViewModel>,
                                   favoritesModel: QuestionListModel?): DetailsModel {
        val questions = (questionsModel?.items ?: emptyList())
                .take(3)

        val processedAnswers = answers
                .filter { it.accepted }
                .take(3)

        val favorites = (favoritesModel?.items ?: emptyList())
                .take(3)

        return DetailsModel(questions, processedAnswers, favorites)
    }
}