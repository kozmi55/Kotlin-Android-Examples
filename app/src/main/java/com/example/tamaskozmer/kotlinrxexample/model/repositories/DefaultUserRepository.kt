package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.CalendarWrapper
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import com.example.tamaskozmer.kotlinrxexample.util.Constants
import com.example.tamaskozmer.kotlinrxexample.util.PreferencesHelper
import io.reactivex.Single
import io.reactivex.SingleEmitter

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class DefaultUserRepository(
        private val userService: UserService,
        private val userDao: UserDao,
        private val connectionHelper: ConnectionHelper,
        private val preferencesHelper: PreferencesHelper,
        private val calendarWrapper: CalendarWrapper) : UserRepository {

    private val LAST_UPDATE_KEY = "last_update_page_"

    override fun getUsers(page: Int, forced: Boolean): Single<UserListModel> {
        return Single.create<UserListModel> { emitter: SingleEmitter<UserListModel> ->
            if (shouldUpdate(page, forced)) {
                loadUsersFromNetwork(page, emitter)
            } else {
                loadOfflineUsers(page, emitter)
            }
        }
    }

    private fun shouldUpdate(page: Int, forced: Boolean) = when {
        forced -> true
        !connectionHelper.isOnline() -> false
        else -> {
            val lastUpdate = preferencesHelper.loadLong(LAST_UPDATE_KEY + page)
            val currentTime = calendarWrapper.getCurrentTimeInMillis()
            lastUpdate + Constants.REFRESH_LIMIT < currentTime
        }
    }

    private fun loadUsersFromNetwork(page: Int, emitter: SingleEmitter<UserListModel>) {
        try {
            val users = userService.getUsers(page).execute().body()
            if (users != null) {
                userDao.insertAll(users.items)
                val currentTime = calendarWrapper.getCurrentTimeInMillis()
                preferencesHelper.saveLong(LAST_UPDATE_KEY + page, currentTime)
                emitter.onSuccess(users)
            } else {
                emitter.onError(Exception("No data received"))
            }
        } catch (exception: Exception) {
            emitter.onError(exception)
        }
    }

    private fun loadOfflineUsers(page: Int, emitter: SingleEmitter<UserListModel>) {
        val users = userDao.getUsers(page)
        if (!users.isEmpty()) {
            emitter.onSuccess(UserListModel(users))
        } else {
            emitter.onError(Exception("Device is offline"))
        }
    }
}