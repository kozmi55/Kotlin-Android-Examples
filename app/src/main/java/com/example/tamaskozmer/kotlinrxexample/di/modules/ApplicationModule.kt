package com.example.tamaskozmer.kotlinrxexample.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.model.persistence.AppDatabase
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.model.services.QuestionService
import com.example.tamaskozmer.kotlinrxexample.model.services.UserService
import com.example.tamaskozmer.kotlinrxexample.util.*
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
    private val DATABASE_NAME = "db-name"

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
    fun provideUserRepository(retrofit: Retrofit, database: AppDatabase, connectionHelper: ConnectionHelper,
                              preferencesHelper: PreferencesHelper, calendarWrapper: CalendarWrapper): UserRepository {
        return UserRepository(
                retrofit.create(UserService::class.java),
                database.userDao(),
                connectionHelper,
                preferencesHelper,
                calendarWrapper)
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(retrofit: Retrofit, database: AppDatabase, connectionHelper: ConnectionHelper,
                                 preferencesHelper: PreferencesHelper, calendarWrapper: CalendarWrapper) : DetailsRepository {
        return DetailsRepository(
                retrofit.create(UserService::class.java),
                retrofit.create(QuestionService::class.java),
                database.questionDao(),
                database.answerDao(),
                database.favoritedByUserDao(),
                connectionHelper,
                preferencesHelper,
                calendarWrapper)
    }

    @Provides
    @Singleton
    fun provideDatabase(context: Context)
            = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME).build()

    @Provides
    @Singleton
    fun provideConnectionHelper(context: Context) = ConnectionHelper(context)

    @Provides
    @Singleton
    fun providePreferencesHelper(context: Context) = PreferencesHelper(context)

    @Provides
    @Singleton
    fun provideCalendarWrapper() = CalendarWrapper()

    @Provides
    @Singleton
    fun provideSchedulerProvider() : SchedulerProvider = AppSchedulerProvider()
}