package com.example.tamaskozmer.kotlinrxexample.presentation

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.view.UserListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserListPresenter(private val userRepository: UserRepository) : BasePresenter<UserListView>() {

    val offset = 5

    var page = 1
    var loading = false

    fun getUsers() {
        loading = true
        userRepository.getUsers(page)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userListModel ->
                    loading = false
                    view?.addUsersToList(userListModel.items)
                    view?.hideLoading()
                    page++
                },
                {
                    loading = false
                    view?.showError()
                    view?.hideLoading()
                })
    }

    fun resetPaging() {
        page = 1
    }

    fun onScrollChanged(lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!loading) {
            if (lastVisibleItemPosition >= totalItemCount - offset) {
                getUsers()
            }
        }

        if (loading && lastVisibleItemPosition >= totalItemCount) {
            view?.showLoading()
        }
    }
}