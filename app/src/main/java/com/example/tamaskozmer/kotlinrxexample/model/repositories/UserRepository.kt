package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService,
        private val userDao: UserDao,
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
}