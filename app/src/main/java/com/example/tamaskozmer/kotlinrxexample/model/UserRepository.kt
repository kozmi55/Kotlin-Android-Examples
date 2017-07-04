package com.example.tamaskozmer.kotlinrxexample.model

import com.google.gson.Gson
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
class UserRepository(private val retrofit: Retrofit) {

    private val userService by lazy { retrofit.create(UserService::class.java) }

    fun getUsers() = userService.getUsers()
}