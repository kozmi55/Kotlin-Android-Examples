package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.Answer
import com.example.tamaskozmer.kotlinrxexample.model.entities.FavoritedByUser
import com.example.tamaskozmer.kotlinrxexample.model.entities.Question
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.AnswerDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.FavoritedByUserDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.QuestionDao
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.CalendarWrapper
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import com.example.tamaskozmer.kotlinrxexample.util.Constants
import com.example.tamaskozmer.kotlinrxexample.util.PreferencesHelper
import io.reactivex.Single
import io.reactivex.SingleEmitter

class DefaultDetailsRepository(
    private val userService: UserService,
    private val questionService: QuestionService,
    private val questionDao: QuestionDao,
    private val answerDao: AnswerDao,
    private val favoritedByUserDao: FavoritedByUserDao,
    private val connectionHelper: ConnectionHelper,
    private val preferencesHelper: PreferencesHelper,
    private val calendarWrapper: CalendarWrapper
) : DetailsRepository {

    override fun getQuestionsByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        val onlineStrategy = {
            val questions = userService.getQuestionsByUser(userId).execute().body()
                    ?.items
                    ?.take(Constants.NUMBER_OF_ITEMS_IN_SECTION)
            questions?.let {
                questionDao.insertAll(questions)
            }
            questions ?: emptyList()
        }

        val offlineStrategy = {
            questionDao.getQuestionsByUser(userId)
        }

        return createSingle<List<Question>>("last_update_questions_by_user_$userId", onlineStrategy, offlineStrategy, forced)
    }

    override fun getAnswersByUser(userId: Long, forced: Boolean): Single<List<Answer>> {
        val onlineStrategy = {
            val answers = userService.getAnswersByUser(userId).execute().body()?.items
                    ?.filter { it.accepted }
                    ?.take(Constants.NUMBER_OF_ITEMS_IN_SECTION)
            answers?.let {
                answerDao.insertAll(answers)
            }
            answers ?: emptyList()
        }

        val offlineStrategy = {
            answerDao.getAnswersByUser(userId)
        }

        return createSingle<List<Answer>>("last_update_answers_by_user_$userId", onlineStrategy, offlineStrategy, forced)
    }

    override fun getFavoritesByUser(userId: Long, forced: Boolean): Single<List<Question>> {
        val onlineStrategy = {
            val questions = userService.getFavoritesByUser(userId).execute().body()?.items
                    ?.take(Constants.NUMBER_OF_ITEMS_IN_SECTION)
            questions?.let {
                questionDao.insertAll(questions)
                val favoritedByUser =
                        FavoritedByUser(userId, questions
                                .map { it.questionId })
                favoritedByUserDao.insert(favoritedByUser)
            }
            questions ?: emptyList()
        }

        val offlineStrategy = {
            val questionIds = favoritedByUserDao.getFavoritesForUser(userId)?.questionIds ?: emptyList()
            questionDao.getQuestionsById(questionIds)
                    .take(Constants.NUMBER_OF_ITEMS_IN_SECTION)
        }

        return createSingle<List<Question>>("last_update_favorites_by_user_$userId", onlineStrategy, offlineStrategy, forced)
    }

    override fun getQuestionsById(ids: List<Long>, userId: Long, forced: Boolean): Single<List<Question>> {
        val onlineStrategy = {
            val questions = questionService.getQuestionsById(ids.joinToString(separator = ";")).execute().body()
            questions?.let {
                questionDao.insertAll(questions.items)
            }
            questions?.items ?: emptyList()
        }

        val offlineStrategy = {
            questionDao.getQuestionsById(ids)
        }

        return createSingle("last_update_questions_by_ids_for_user_$userId", onlineStrategy, offlineStrategy, forced)
    }

    private fun <T> createSingle(lastUpdateKey: String, onlineStrategy: () -> T, offlineStrategy: () -> T, forced: Boolean): Single<T> {
        return Single.create<T> { emitter: SingleEmitter<T> ->
            if (shouldUpdate(lastUpdateKey, forced)) {
                try {
                    val onlineResults = onlineStrategy()
                    val currentTime = calendarWrapper.getCurrentTimeInMillis()
                    preferencesHelper.saveLong(lastUpdateKey, currentTime)
                    emitter.onSuccess(onlineResults)
                } catch (exception: Exception) {
                    emitter.onError(exception)
                }
            } else {
                val offlineResults = offlineStrategy()
                emitter.onSuccess(offlineResults)
            }
        }
    }

    private fun shouldUpdate(lastUpdateKey: String, forced: Boolean) = when {
        forced -> true
        !connectionHelper.isOnline() -> false
        else -> {
            val lastUpdate = preferencesHelper.loadLong(lastUpdateKey)
            val currentTime = calendarWrapper.getCurrentTimeInMillis()
            lastUpdate + Constants.REFRESH_LIMIT < currentTime
        }
    }
}