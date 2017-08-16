package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.util.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Module
class UserListFragmentModule() {
    @Provides
    fun provideGetUsers(userRepository: UserRepository) = GetUsers(userRepository)

    @Provides
    fun providePresenter(getUsers: GetUsers, schedulerProvider: SchedulerProvider)
            = UserListPresenter(getUsers, schedulerProvider)
}