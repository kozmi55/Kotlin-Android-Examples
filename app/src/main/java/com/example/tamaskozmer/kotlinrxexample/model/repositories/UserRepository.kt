package com.example.tamaskozmer.kotlinrxexample.model.repositories

import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import com.example.tamaskozmer.kotlinrxexample.model.persistence.daos.UserDao
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.CalendarWrapper
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import com.example.tamaskozmer.kotlinrxexample.util.Constants
import com.example.tamaskozmer.kotlinrxexample.util.PreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(
        private val userService: UserService,
        private val userDao: UserDao,
        private val connectionHelper: ConnectionHelper,
        private val preferencesHelper: PreferencesHelper,
        private val calendarWrapper: CalendarWrapper) {

    private val LAST_UPDATE_KEY = "last_update_page_"

    val usersLiveData = MediatorLiveData<List<User>>()

    fun getUsers(page: Int = 1, forced: Boolean = false, loadingLiveData: MutableLiveData<Boolean>) {
        val users = userDao.getUsers(page)
        usersLiveData.addSource(users) {
            usersLiveData.value = it
        }
        if (shouldUpdate(page, forced)) {
            loadUsersFromNetwork(page, loadingLiveData)
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

    private fun loadUsersFromNetwork(page: Int, loadingLiveData: MutableLiveData<Boolean>) {
        loadingLiveData.value = true
        userService.getUsers(page).enqueue(object : Callback<UserListModel> {
            override fun onFailure(call: Call<UserListModel>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<UserListModel>?, response: Response<UserListModel>?) {
                if (response != null) {
                    val items = response.body()?.items
                    items?.let {
                        Thread(Runnable {
                            userDao.insertAll(items)
                            loadingLiveData.postValue(false)
                        }).start()
                    }
                    val currentTime = calendarWrapper.getCurrentTimeInMillis()
                    preferencesHelper.saveLong(LAST_UPDATE_KEY + page, currentTime)
                }
            }
        })
    }

    fun increaseRep(user: User) {
        Thread(Runnable {
            userDao.insert(user)
        }).start()
    }
}