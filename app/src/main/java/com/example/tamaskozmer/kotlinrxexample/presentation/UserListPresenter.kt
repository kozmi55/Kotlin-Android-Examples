package com.example.tamaskozmer.kotlinrxexample.presentation

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.view.MainView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserListPresenter(private val userRepository: UserRepository) : BasePresenter<MainView>() {

    var page = 1

    fun getUsers() {
        userRepository.getUsers(page)
                .delay(2, TimeUnit.SECONDS) // TODO Delay just for testing
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userListModel ->
                    view?.addUsersToList(userListModel.items)
                    view?.hideLoading()
                    page++
                },
                {
                    view?.showError()
                    view?.hideLoading()
                })
    }

    fun resetPaging() {
        page = 1
    }
}