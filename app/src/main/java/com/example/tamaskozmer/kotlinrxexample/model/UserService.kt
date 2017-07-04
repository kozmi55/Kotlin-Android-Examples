package com.example.tamaskozmer.kotlinrxexample.model

import com.example.tamaskozmer.kotlinrxexample.model.entities.UserListModel
import retrofit.http.GET
import retrofit.http.Query
import rx.Single

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
interface UserService {
    @GET("/users?order=desc&sort=reputation&site=stackoverflow")
    fun getUsers(@Query("page") page: Int = 1) : Single<UserListModel>
}