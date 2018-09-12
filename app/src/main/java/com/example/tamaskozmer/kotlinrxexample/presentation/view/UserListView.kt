package com.example.tamaskozmer.kotlinrxexample.presentation.view

import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewdata.UserViewData

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
interface UserListView {
    fun showLoading()
    fun hideLoading()
    fun addUsersToList(users: List<UserViewData>)
    fun showEmptyListError()
    fun hideEmptyListError()
    fun showToastError()
    fun clearList()
}