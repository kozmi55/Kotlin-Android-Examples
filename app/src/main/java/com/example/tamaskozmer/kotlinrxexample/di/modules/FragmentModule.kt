package com.example.tamaskozmer.kotlinrxexample.di.modules

import com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments.DetailsFragment
import com.example.tamaskozmer.kotlinrxexample.presentation.view.fragments.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeUserListFragment(): UserListFragment

    @ContributesAndroidInjector
    abstract fun contributeUserDetailsFragment(): DetailsFragment
}