package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.AnswerViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.QuestionViewModel
import io.reactivex.Single
import io.reactivex.functions.Function3
import javax.inject.Inject

class GetDetails @Inject constructor(private val detailsRepository: DetailsRepository) {

    fun execute(userId: Long, forced: Boolean): Single<DetailsViewModel> {
        return Single.zip(
                detailsRepository.getQuestionsByUser(userId, forced),
                getTitlesForAnswers(userId, forced),
                detailsRepository.getFavoritesByUser(userId, forced),
                Function3<List<Question>, List<AnswerViewModel>, List<Question>, DetailsViewModel>
                { questions, answers, favorites ->
                    createDetailsModel(questions, answers, favorites) })
    }

    private fun getTitlesForAnswers(userId: Long, forced: Boolean): Single<List<AnswerViewModel>> {
        return detailsRepository.getAnswersByUser(userId, forced)
                .flatMap { answers: List<Answer> ->
                    mapAnswersToAnswerViewModels(answers, userId, forced) }
    }

    private fun mapAnswersToAnswerViewModels(answers: List<Answer>, userId: Long, forced: Boolean): Single<List<AnswerViewModel>> {
        val ids = answers
                .map { it.questionId }

        val questionsById = detailsRepository.getQuestionsById(ids, userId, forced)

        return questionsById
                .map { questions: List<Question> ->
                    createAnswerViewModels(answers, questions) }
    }

    private fun createAnswerViewModels(answers: List<Answer>, questions: List<Question>): List<AnswerViewModel> {
        return answers.map { (answerId, questionId, score, accepted) ->
            val question = questions.find { it.questionId == questionId }
            AnswerViewModel(answerId, score, accepted, question?.title ?: "Unknown")
        }
    }

    private fun createDetailsModel(
        questions: List<Question>,
        answers: List<AnswerViewModel>,
        favorites: List<Question>
    ): DetailsViewModel {
        val questionViewModels =
                questions.map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }
        val favoriteViewModels =
                favorites.map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }

        return DetailsViewModel(questionViewModels, answers, favoriteViewModels)
    }
}