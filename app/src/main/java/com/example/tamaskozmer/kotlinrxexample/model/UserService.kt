package com.example.tamaskozmer.kotlinrxexample.model

import retrofit.http.GET
import retrofit.http.Query
import rx.Single

/**
 * Created by Tamas_Kozmer on 7/3/2017.
 */
interface UserService {
    @GET("/users")
    fun getUsers(
            @Query("order") order: String = "desc",
            @Query("sort") sort: String = "reputation",
            @Query("site") site: String = "stackoverflow") : Single<Items>
}