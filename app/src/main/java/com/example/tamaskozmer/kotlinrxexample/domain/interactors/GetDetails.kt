package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.AnswerViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.DetailsViewData
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.QuestionViewData
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetDetails @Inject constructor(private val detailsRepository: DetailsRepository) {

    fun execute(userId: Long, forced: Boolean): Single<DetailsViewData> {
        return Single.zip(
                detailsRepository.getQuestionsByUser(userId, forced),
                getTitlesForAnswers(userId, forced),
                detailsRepository.getFavoritesByUser(userId, forced),
                Function3<List<Question>, List<AnswerViewData>, List<Question>, DetailsViewData>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun getTitlesForAnswers(userId: Long, forced: Boolean): Single<List<AnswerViewData>> {
        return detailsRepository.getAnswersByUser(userId, forced)
                .flatMap { answers: List<Answer> ->
                    mapAnswersToAnswerViewDatas(answers, userId, forced) }
    }

    private fun mapAnswersToAnswerViewDatas(answers: List<Answer>, userId: Long, forced: Boolean): Single<List<AnswerViewData>> {
        val ids = answers
                .map { it.questionId }

        val questionsById = detailsRepository.getQuestionsById(ids, userId, forced)

        return questionsById
                .map { questions: List<Question> ->
                    createAnswerViewDatas(answers, questions) }
    }

    private fun createAnswerViewDatas(answers: List<Answer>, questions: List<Question>): List<AnswerViewData> {
        return answers.map { (answerId, questionId, score, accepted) ->
            val question = questions.find { it.questionId == questionId }
            AnswerViewData(answerId, score, accepted, question?.title ?: "Unknown")
        }
    }

    private fun createDetailsModel(
        questions: List<Question>,
        answers: List<AnswerViewData>,
        favorites: List<Question>
    ): DetailsViewData {
        val QuestionViewDatas =
                questions.map { QuestionViewData(it.viewCount, it.score, it.title, it.link, it.questionId) }
        val favoriteViewModels =
                favorites.map { QuestionViewData(it.viewCount, it.score, it.title, it.link, it.questionId) }

        return DetailsViewData(QuestionViewDatas, answers, favoriteViewModels)
    }
}