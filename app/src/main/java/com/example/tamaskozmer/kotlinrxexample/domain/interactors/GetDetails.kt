package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerList
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionList
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.AnswerViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.QuestionViewModel
import io.reactivex.Single
import io.reactivex.functions.Function3

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
class GetDetails(private val detailsRepository: DetailsRepository) {

    fun execute(userId: Long) : Single<DetailsViewModel> {
        return Single.zip(
                detailsRepository.getQuestionsByUser(userId),
                getTitlesForAnswers(userId),
                detailsRepository.getFavoritesByUser(userId),
                Function3<QuestionList, List<AnswerViewModel>, QuestionList, DetailsViewModel>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun getTitlesForAnswers(userId: Long) : Single<List<AnswerViewModel>> {
        return detailsRepository.getAnswersByUser(userId)
                .flatMap { answerList: AnswerList? ->
                    mapAnswersToAnswersWithTitle(answerList?.items ?: emptyList(), userId) }
    }

    private fun mapAnswersToAnswersWithTitle(answers: List<Answer>, userId: Long): Single<List<AnswerViewModel>> {
        val ids = answers
                .map { it.questionId }

        val questionsListModel = detailsRepository.getQuestionsById(ids, userId)

        return questionsListModel
                .flatMap { questionListModel: QuestionList? -> Single.just(questionListModel?.items) }
                .map { questions: List<Question>? -> addTitlesToAnswers(answers, questions?: emptyList()) }
    }

    private fun addTitlesToAnswers(answers: List<Answer>, questions: List<Question>) : List<AnswerViewModel> {
        return answers.map { (answerId, questionId, score, accepted) ->
            val question = questions.find { it.questionId == questionId }
            AnswerViewModel(answerId, score, accepted, question?.title ?: "Unknown")
        }
    }

    private fun createDetailsModel(questionsModel: QuestionList?, answers: List<AnswerViewModel>,
                                   favoritesModel: QuestionList?): DetailsViewModel {
        val questions = (questionsModel?.items ?: emptyList())
                .map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }

        val favorites = (favoritesModel?.items ?: emptyList())
                .map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }

        return DetailsViewModel(questions, answers, favorites)
    }
}