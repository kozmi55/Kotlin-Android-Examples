package com.example.tamaskozmer.kotlinrxexample.mocks.di.modules

import com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories.FakeDetailsRepository
import com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories.FakeUserRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.util.AppSchedulerProvider
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestApplicationModule(
        private val userRepository: UserRepository = FakeUserRepository(),
        private val detailsRepository: DetailsRepository = FakeDetailsRepository()) {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return userRepository
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(): DetailsRepository {
        return detailsRepository
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}