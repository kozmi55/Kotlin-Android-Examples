package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Module
class ApplicationModule(val application: CustomApplication) {
    private val BASE_URL = "https://api.stackexchange.com/2.2/"

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideUserRepository(retrofit: Retrofit) = UserRepository(retrofit)
}