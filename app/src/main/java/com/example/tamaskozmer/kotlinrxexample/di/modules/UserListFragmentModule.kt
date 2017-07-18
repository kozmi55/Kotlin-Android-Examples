package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.domain.interactors.GetUsers
import com.example.tamaskozmer.kotlinrxexample.model.repositories.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.view.fragments.UserListFragment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Module
class UserListFragmentModule(val fragment: UserListFragment) {
    @Provides
    fun provideGetUsers(userRepository: UserRepository) = GetUsers(userRepository)

    @Provides
    @Singleton
    fun providePresenter(getUsers: GetUsers) = UserListPresenter(getUsers)
}