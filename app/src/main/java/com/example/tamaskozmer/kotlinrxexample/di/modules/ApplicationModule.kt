package com.example.tamaskozmer.kotlinrxexample.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.model.persistence.AppDatabase
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.ConnectionHelper
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Module
class ApplicationModule(val application: CustomApplication) {
    private val BASE_URL = "https://api.stackexchange.com/2.2/"

    @Provides
    @Singleton
    fun provideAppContext() : Context = application

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideUserRepository(retrofit: Retrofit, database: AppDatabase, connectionHelper: ConnectionHelper): UserRepository {
        return UserRepository(
                retrofit.create(UserService::class.java),
                retrofit.create(QuestionService::class.java),
                database.userDao(),
                database.questionDao(),
                database.answerDao(),
                connectionHelper)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context)
            = Room.databaseBuilder(context, AppDatabase::class.java, "db-name").build()

    @Provides
    @Singleton
    fun provideConnectionHelper(context: Context) = ConnectionHelper(context)
}