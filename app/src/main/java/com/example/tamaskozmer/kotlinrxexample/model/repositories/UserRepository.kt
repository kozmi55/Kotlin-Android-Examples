package com.example.tamaskozmer.kotlinrxexample.model.repositories

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import io.reactivex.Single

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
interface UserRepository {
    fun getUsers(page: Int = 1, forced: Boolean = false): Single<UserListModel>
}