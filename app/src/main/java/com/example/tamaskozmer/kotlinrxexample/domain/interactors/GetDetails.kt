package com.example.tamaskozmer.kotlinrxexample.domain.interactors

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerList
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionList
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.AnswerViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.DetailsViewModel
import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.QuestionViewModel
import io.reactivex.Single
import io.reactivex.functions.BiFunction

/**
 * Created by Tamas_Kozmer on 7/14/2017.
 */
class GetDetails(private val userRepository: UserRepository) {

    fun execute(userId: Long) : Single<DetailsViewModel> {
        // TODO The original, use this when favorites will be offline capable
//        return Single.zip(
//                userRepository.getQuestionsByUser(userId),
//                getTitlesForAnswers(userId),
//                userRepository.getFavoritesByUser(userId),
//                Function3<QuestionList, List<AnswerViewModel>, QuestionList, DetailsViewModel>
//                { questions, answers, favorites ->
//                    createDetailsModel(questions, answers, favorites) })

        return Single.zip(
                userRepository.getQuestionsByUser(userId),
                getTitlesForAnswers(userId),
                BiFunction<QuestionList, List<AnswerViewModel>, DetailsViewModel>
                { questions, answers ->
                    createDetailsModel(questions, answers, QuestionList(emptyList())) })
    }

    private fun getTitlesForAnswers(userId: Long) : Single<List<AnswerViewModel>> {
        return userRepository.getAnswersByUser(userId)
                .flatMap { answerList: AnswerList? ->
                    mapAnswersToAnswersWithTitle(answerList?.items ?: emptyList()) }
    }

    private fun mapAnswersToAnswersWithTitle(answers: List<Answer>): Single<List<AnswerViewModel>> {
        val processedAnswers = answers
                .filter { it.accepted }
                .take(3)

        val ids = processedAnswers
                .map { it.questionId }

        val questionsListModel = userRepository.getQuestionsById(ids)

        return questionsListModel
                .flatMap { questionListModel: QuestionList? -> Single.just(questionListModel?.items) }
                .map { questions: List<Question>? -> addTitlesToAnswers(processedAnswers, questions?: emptyList()) }
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
                .take(3)
                .map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }

        val favorites = (favoritesModel?.items ?: emptyList())
                .take(3)
                .map { QuestionViewModel(it.viewCount, it.score, it.title, it.link, it.questionId) }

        return DetailsViewModel(questions, answers, favorites)
    }
}