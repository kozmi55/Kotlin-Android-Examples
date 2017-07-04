package com.example.tamaskozmer.kotlinrxexample.presentation

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.view.MainView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserListPresenter(private val userRepository: UserRepository) : BasePresenter<MainView>() {

    fun getUsers() {
        userRepository.getUsers()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userListModel ->
                    view?.showUsers(userListModel.items)
                    view?.hideLoading()
                },
                {
                    view?.showError()
                    view?.hideLoading()
                })
    }
}