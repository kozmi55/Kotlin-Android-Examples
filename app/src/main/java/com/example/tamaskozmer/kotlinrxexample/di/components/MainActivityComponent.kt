package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.MainActivityModule
import com.example.tamaskozmer.kotlinrxexample.presentation.UserListPresenter
import com.example.tamaskozmer.kotlinrxexample.view.activities.MainActivity
import dagger.Subcomponent
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Singleton
@Subcomponent(modules = arrayOf(MainActivityModule::class))
interface MainActivityComponent {
    fun inject(activity: MainActivity)
    fun presenter() : UserListPresenter
}