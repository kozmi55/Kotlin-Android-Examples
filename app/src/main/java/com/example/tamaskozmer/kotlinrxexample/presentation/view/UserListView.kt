package com.example.tamaskozmer.kotlinrxexample.presentation.view

import com.example.tamaskozmer.kotlinrxexample.presentation.view.viewmodels.UserViewModel

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
interface UserListView {
    fun showLoading()
    fun hideLoading()
    fun addUsersToList(users: List<UserViewModel>, clearList: Boolean = false)
    fun showError()
    fun clearList()
}