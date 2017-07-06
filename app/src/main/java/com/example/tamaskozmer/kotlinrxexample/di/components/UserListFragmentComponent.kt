package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.UserListFragmentModule
import com.example.tamaskozmer.kotlinrxexample.presentation.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.view.fragments.UserListFragment
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(UserListFragmentModule::class))
interface UserListFragmentComponent {
    fun inject(fragment: UserListFragment)
    fun presenter() : UserListPresenter
}