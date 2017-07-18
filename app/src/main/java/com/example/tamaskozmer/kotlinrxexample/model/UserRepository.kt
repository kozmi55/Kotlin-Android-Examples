package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.AnswerList
import com.example.tamaskozmer.kotlinrxexample.model.entities.FavoritedByUser
import com.example.tamaskozmer.kotlinrxexample.model.entities.QuestionList
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.AnswerDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.FavoritedByUserDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.QuestionDao
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService,
        private val questionService: QuestionService,
        private val userDao: UserDao,
        private val questionDao: QuestionDao,
        private val answerDao: AnswerDao,
        private val favoritedByUserDao: FavoritedByUserDao,
        private val connectionHelper: ConnectionHelper) {

    fun getUsers(page: Int = 1, forced: Boolean = false): Single<UserListModel> {
        return Single.create<UserListModel> { emitter: SingleEmitter<UserListModel>? ->
            if (connectionHelper.isOnline() || forced) {
                try {
                    val users = userService.getUsers(page).execute().body()
                    users?.let {
                        userDao.insertAll(users.items)
                    }
                    emitter?.onSuccess(users)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                handleOfflineUsers(page, emitter, Exception("Device is offline"))
            }
        }
    }

    private fun handleOfflineUsers(page: Int, emitter: SingleEmitter<UserListModel>?, exception: Exception) {
        if (page == 1) {
            val allUsers = userDao.getAllUsers()
            if (!allUsers.isEmpty()) {
                emitter?.onSuccess(UserListModel(allUsers))
            } else {
                emitter?.onError(exception)
            }
        } else {
            emitter?.onError(exception)
        }
    }

    fun getQuestionsByUser(userId: Long): Single<QuestionList> {
        return Single.create<QuestionList> { emitter: SingleEmitter<QuestionList>? ->
            if (connectionHelper.isOnline()) {
                try {
                    val questions = userService.getQuestionsByUser(userId).execute().body()
                    questions?.let {
                        val questionsWithOwnerId = questions.items.map { it.copy(ownerId = userId) }
                        questionDao.insertAll(questionsWithOwnerId)
                    }
                    emitter?.onSuccess(questions)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                val questionsFromDb = questionDao.getQuestionsByUser(userId)
                emitter?.onSuccess(QuestionList(questionsFromDb))
            }
        }
    }

    fun getAnswersByUser(userId: Long): Single<AnswerList> {
        return Single.create<AnswerList> { emitter: SingleEmitter<AnswerList>? ->
            if (connectionHelper.isOnline()) {
                try {
                    val answers = userService.getAnswersByUser(userId).execute().body()
                    answers?.let {
                        val answersWithOwnerId = answers.items.map { it.copy(ownerId = userId) }
                        answerDao.insertAll(answersWithOwnerId)
                    }
                    emitter?.onSuccess(answers)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                val answersFromDb = answerDao.getAnswersByUser(userId)
                emitter?.onSuccess(AnswerList(answersFromDb))
            }
        }
    }

    fun getFavoritesByUser(userId: Long): Single<QuestionList> {
        return Single.create<QuestionList> { emitter: SingleEmitter<QuestionList>? ->
            if (connectionHelper.isOnline()) {
                try {
                    val questions = userService.getFavoritesByUser(userId).execute().body()
                    questions?.let {
                        // TODO Owner ids will be missed, if we get a question what is already stored for a user
                        questionDao.insertAll(questions.items)
                        val favoritedByUser =
                                FavoritedByUser(userId, questions.items.map { it.questionId })
                        favoritedByUserDao.insert(favoritedByUser)
                    }
                    emitter?.onSuccess(questions)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                val questionIds = favoritedByUserDao.getFavoritesForUser(userId).questionIds
                val questionsFromDb = questionDao.getQuestionsById(questionIds)
                emitter?.onSuccess(QuestionList(questionsFromDb))
            }
        }
    }

    fun getQuestionsById(ids: List<Long>): Single<QuestionList> {
        return Single.create<QuestionList> { emitter: SingleEmitter<QuestionList>? ->
            if (connectionHelper.isOnline()) {
                try {
                    val questions = questionService.getQuestionsById(ids.joinToString(separator = ";")).execute().body()
                    questions?.let {
                        // TODO Owner ids will be missed, if we get a question what is already stored for a user
                        questionDao.insertAll(questions.items)
                    }
                    emitter?.onSuccess(questions)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                val questionsFromDb = questionDao.getQuestionsById(ids)
                emitter?.onSuccess(QuestionList(questionsFromDb))
            }
        }
    }
}