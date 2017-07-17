package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
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
        private val connectionHelper: ConnectionHelper) {

    fun getUsers(page: Int = 1, forced: Boolean = false): Single<UserListModel> {
        return Single.create<UserListModel> { emitter: SingleEmitter<UserListModel>? ->
            if (connectionHelper.isOnline() || forced) {
                try {
                    val users = userService.getUsers(page).execute().body()
                    userDao.insertAll(users?.items ?: emptyList())
                    emitter?.onSuccess(users)
                } catch (exception: Exception) {
                    emitter?.onError(exception)
                }
            } else {
                handleOffline(page, emitter, Exception("Device is offline"))
            }
        }
    }

    private fun handleOffline(page: Int, emitter: SingleEmitter<UserListModel>?, exception: Exception) {
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

    fun getQuestionsByUser(userId: Long) = userService.getQuestionsByUser(userId)

    fun getAnswersByUser(userId: Long) = userService.getAnswersByUser(userId)

    fun getFavoritesByUser(userId: Long) = userService.getFavoritesByUser(userId)

    fun getQuestionsById(ids: String) = questionService.getQuestionById(ids)
}