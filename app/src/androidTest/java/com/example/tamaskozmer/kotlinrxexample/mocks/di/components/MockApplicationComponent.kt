package com.example.tamaskozmer.kotlinrxexample.mocks.di.components

import com.example.tamaskozmer.kotlinrxexample.di.components.ApplicationComponent
import com.example.tamaskozmer.kotlinrxexample.mocks.di.modules.MockApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Tamas_Kozmer on 8/8/2017.
 */
@Singleton
@Component(modules = arrayOf(MockApplicationModule::class))
interface MockApplicationComponent : ApplicationComponent