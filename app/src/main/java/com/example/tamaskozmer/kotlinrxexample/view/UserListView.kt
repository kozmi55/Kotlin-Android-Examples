package com.example.tamaskozmer.kotlinrxexample.view

import com.example.tamaskozmer.kotlinrxexample.model.entities.User

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
interface UserListView {
    fun showLoading()
    fun hideLoading()
    fun addUsersToList(users: List<User>)
    fun showError()
}