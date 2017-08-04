package com.example.tamaskozmer.kotlinrxexample.presentation.presenters

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.presentation.view.UserListView
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserListPresenter(
        private val getUsers: GetUsers,
        private val schedulerProvider: SchedulerProvider) : BasePresenter<UserListView>() {

    private val offset = 5

    private var page = 1
    private var loading = false

    fun getUsers(forced: Boolean = false) {
        loading = true
        val pageToRequest = if (forced) 1 else page
        getUsers.execute(pageToRequest, forced)
                .subscribeOn(schedulerProvider.ioScheduler())
                .observeOn(schedulerProvider.uiScheduler())
                .subscribe({
                    users ->
                    loading = false
                    if (forced) {
                        resetPaging()
                    }
                    if (page == 1) {
                        view?.clearList()
                    }
                    view?.addUsersToList(users)
                    view?.hideLoading()
                    page++
                },
                {
                    loading = false
                    view?.showError()
                    view?.hideLoading()
                })
    }

    private fun resetPaging() {
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