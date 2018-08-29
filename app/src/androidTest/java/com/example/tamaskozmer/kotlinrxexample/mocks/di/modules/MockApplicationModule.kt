package com.example.tamaskozmer.kotlinrxexample.mocks.di.modules

import com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories.MockDetailsRepository
import com.example.tamaskozmer.kotlinrxexample.mocks.model.repositories.MockUserRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.DetailsRepository
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.util.AppSchedulerProvider
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MockApplicationModule {

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository {
        return MockUserRepository()
    }

    @Provides
    @Singleton
    fun provideDetailsRepository(): DetailsRepository {
        return MockDetailsRepository()
    }

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()
}