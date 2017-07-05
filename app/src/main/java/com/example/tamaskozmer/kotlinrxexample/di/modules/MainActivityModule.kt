package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.model.UserRepository
import com.example.tamaskozmer.kotlinrxexample.presentation.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Module
class MainActivityModule(val activity: MainActivity) {
    @Provides
    @Singleton
    fun providePresenter(userRepository: UserRepository) = UserListPresenter(userRepository)
}