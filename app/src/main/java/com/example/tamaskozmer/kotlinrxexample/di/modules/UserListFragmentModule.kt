package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
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
    @Singleton
    fun providePresenter(userRepository: UserRepository) = UserListPresenter(userRepository)
}