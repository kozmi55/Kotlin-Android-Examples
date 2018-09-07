package com.example.tamaskozmer.kotlinrxexample.di.components

import com.example.tamaskozmer.kotlinrxexample.CustomApplication
import com.example.tamaskozmer.kotlinrxexample.di.modules.ApplicationModule
import com.example.tamaskozmer.kotlinrxexample.di.modules.FragmentModule
import com.example.tamaskozmer.kotlinrxexample.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, FragmentModule::class, AndroidSupportInjectionModule::class, ViewModelModule::class])
interface ApplicationComponent : AndroidInjector<CustomApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<CustomApplication>()
}