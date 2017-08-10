package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.example.tamaskozmer.kotlinrxexample.model.entities.User
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserListViewModel() : ViewModel() {
    private lateinit var userRepository: UserRepository

    var page = 1

    val offset = 5

    var loadingLiveData:  MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    var usersLiveData: LiveData<List<User>>? = null

    fun init(userRepository: UserRepository) {
        if (usersLiveData != null) {
            return
        }
        this.userRepository = userRepository
        usersLiveData = userRepository.usersLiveData
        loadingLiveData.value = false

        userRepository.getUsers(page, false, loadingLiveData)
        page++
    }

    fun refresh() {
        page = 1
        userRepository.getUsers(page, true, loadingLiveData)
    }

    fun increaseRep(user: User) {
        userRepository.increaseRep(user.copy(reputation = user.reputation + 1))
    }

    fun onScrollChanged(lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!(loadingLiveData.value ?: false)) {
            if (lastVisibleItemPosition >= totalItemCount - offset) {
                userRepository.getUsers(page, false, loadingLiveData)
                page++
            }
        }

//        if (loading && lastVisibleItemPosition >= totalItemCount) {
//            view?.showLoading()
//        }
    }
}