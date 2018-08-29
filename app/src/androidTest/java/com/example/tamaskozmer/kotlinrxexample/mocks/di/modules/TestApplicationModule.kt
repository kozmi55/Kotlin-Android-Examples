package com.example.tamaskozmer.kotlinrxexample.mocks.di.modules

import com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories.FakeDetailsRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.util.AppSchedulerProvider
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule(private val userRepository: UserRepository) {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(): DetailsRepository {
        return FakeDetailsRepository()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}