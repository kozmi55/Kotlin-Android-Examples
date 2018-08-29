package com.example.tamaskozmer.kotlinrxexample.mocks.di.components

import com.example.tamaskozmer.kotlinrxexample.di.modules.FragmentModule
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.TestApplicationModule
import com.example.tamaskozmer.kotlinrxexample.testutil.TestApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [TestApplicationModule::class, FragmentModule::class, AndroidSupportInjectionModule::class])
interface TestApplicationComponent : AndroidInjector<TestApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<TestApplication>() {
        abstract fun appModule(appModule: TestApplicationModule): Builder
    }
}