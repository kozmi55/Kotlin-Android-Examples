package com.example.tamaskozmer.kotlinrxexample.model

import com.google.gson.Gson
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository {
    private val BASE_URL = "https://api.stackexchange.com/2.2/"

    private val retrofit: Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .build()

    private val userService = retrofit.create(UserService::class.java)

    fun getUsers() = userService.getUsers()
}