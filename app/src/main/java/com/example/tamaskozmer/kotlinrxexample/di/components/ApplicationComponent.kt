package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.di.modules.ApplicationModule
import com.example.tamaskozmer.kotlinrxexample.di.modules.DetailFragmentModule
import com.example.tamaskozmer.kotlinrxexample.di.modules.UserListFragmentModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 7/4/2017.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface ApplicationComponent {
    fun inject(application: CustomApplication)
    fun plus(userListFragmentModule: UserListFragmentModule) : UserListFragmentComponent
    fun plus(detailFragmentModule: DetailFragmentModule) : DetailFragmentComponent
}