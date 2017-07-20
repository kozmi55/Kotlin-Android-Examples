package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.UserListFragmentModule
import com.example.tamaskozmer.kotlinrxexample.presentation.presenters.UserListPresenter
import dagger.Subcomponent

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Subcomponent(modules = arrayOf(UserListFragmentModule::class))
interface UserListFragmentComponent {
    fun presenter() : UserListPresenter
}