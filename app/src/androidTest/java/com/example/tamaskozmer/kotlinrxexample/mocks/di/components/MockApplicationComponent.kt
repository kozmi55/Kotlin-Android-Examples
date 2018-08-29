package com.example.tamaskozmer.kotlinrxexample.mocks.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.FragmentModule
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.MockApplicationModule
import com.example.tamaskozmer.kotlinrxexample.testutil.TestApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [MockApplicationModule::class, FragmentModule::class, AndroidSupportInjectionModule::class])
interface MockApplicationComponent : AndroidInjector<TestApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestApplication>() {
        abstract fun appModule(appModule: MockApplicationModule): Builder
    }
}